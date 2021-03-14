@file:Suppress("UNUSED", "MemberVisibilityCanBePrivate")

package com.jarvis.client

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.jarvis.client.data.ClientJsonMapper
import com.jarvis.client.data.JarvisConfig
import java.io.File
import java.io.FileOutputStream

class JarvisClient internal constructor(
    private val context: Context,
    private val jsonMapper: ClientJsonMapper
) {
    enum class PushConfigResult {
        SUCCESS,
        FAILURE,
        LOCKED
    }

    var loggingEnabled = true

    fun pushConfigToJarvisApp(config: JarvisConfig): PushConfigResult {

        log("Pushing config to the Jarvis app.")

        @SuppressLint("QueryPermissionsNeeded")
        val queryResult = context.packageManager.getInstalledPackages(PackageManager.GET_PROVIDERS)
            .firstOrNull {
                it.packageName == PACKAGE
            }
        log("- Jarvis app ContentProvider query: ${if (queryResult != null) "Success" else "Failure"}.")

        val jarvisConfigFile = writeConfigToFile(config)
        log("- Writing Jarvis config to local file `$jarvisConfigFile`.")

        val jarvisConfigFileUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.$JARVIS_CONFIG_PROVIDER",
            jarvisConfigFile
        )

        log("- Granting Jarvis app permission for URI '$jarvisConfigFileUri' from package '${context.packageName}'.")
        context.grantUriPermission(
            PACKAGE,
            jarvisConfigFileUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        val success: Int = try {
            val configUri = CONFIG_BASE_URI.buildUpon()
                .appendQueryParameter(JARVIS_CONFIG_FILE_URI, jarvisConfigFileUri.toString())
                .build()

            log("- Pushing config file URI to Jarvis app.")
            var cursorResult = JARVIS_PUSH_CONFIG_RESULT_CURSOR_ERROR
            context.contentResolver.query(
                configUri,
                null,
                null,
                null,
                null
            )?.use {
                cursorResult = if (!it.moveToFirst()) {
                    JARVIS_PUSH_CONFIG_RESULT_FAILURE
                } else {
                    it.getString(COLUMN_VALUE_INDEX).toInt()
                }
            }

            val message = when (cursorResult) {
                JARVIS_PUSH_CONFIG_RESULT_SUCCESS -> "Success"
                JARVIS_PUSH_CONFIG_RESULT_FAILURE -> "Failure"
                JARVIS_PUSH_CONFIG_RESULT_LOCKED -> "Failure: Jarvis is locked"
                else -> "Failure: Query error"
            }
            log("- $message ($cursorResult).")

            cursorResult
        } catch (error: Throwable) {
            log("- Error: ${error.message}")
            JARVIS_PUSH_CONFIG_RESULT_FAILURE
        } finally {
            log("- Revoking URI permission.")
            context.revokeUriPermission(
                jarvisConfigFileUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        log("- Done.")
        return when (success) {
            JARVIS_PUSH_CONFIG_RESULT_SUCCESS -> PushConfigResult.SUCCESS
            JARVIS_PUSH_CONFIG_RESULT_LOCKED -> PushConfigResult.LOCKED
            else -> PushConfigResult.FAILURE
        }
    }

    fun getString(name: String, defaultValue: String): String =
        getString(name) { defaultValue }

    fun getString(name: String, lazyDefaultValue: () -> String): String =
        safeGetConfig(name, lazyDefaultValue, String::toString)

    fun getBoolean(name: String, defaultValue: Boolean): Boolean =
        getBoolean(name) { defaultValue }

    fun getBoolean(name: String, lazyDefaultValue: () -> Boolean): Boolean =
        safeGetConfig(name, lazyDefaultValue, String::toBoolean)

    fun getLong(name: String, defaultValue: Long): Long =
        getLong(name) { defaultValue }

    fun getLong(name: String, lazyDefaultValue: () -> Long): Long =
        safeGetConfig(name, lazyDefaultValue, String::toLong)

    fun getDouble(name: String, defaultValue: Double): Double =
        getDouble(name) { defaultValue }

    fun getDouble(name: String, lazyDefaultValue: () -> Double): Double =
        safeGetConfig(name, lazyDefaultValue, String::toDouble)

    fun getStringListSelection(name: String, defaultValue: Int): Int =
        getStringListSelection(name) { defaultValue }

    fun getStringListSelection(name: String, lazyDefaultValue: () -> Int): Int =
        safeGetConfig(name, lazyDefaultValue, String::toInt)

    private inline fun <reified T : Any> safeGetConfig(
        name: String,
        noinline lazyDefaultValue: () -> T,
        asDataType: String.() -> T
    ): T = try {
        log("Reading '$name' as ${T::class.java.simpleName}.")
        val value = getConfigValue(name, lazyDefaultValue).asDataType()
        log("- Success: Returning value '$value'.")
        value
    } catch (error: Throwable) {
        val defaultValue = lazyDefaultValue()
        log("- Failed: $error. Returning default value '$defaultValue'.")
        defaultValue
    }

    private fun <T : Any> getConfigValue(
        name: String,
        lazyDefaultValue: () -> T
    ): String {
        val configUri = CONFIG_BASE_URI.buildUpon()
            .appendEncodedPath(COLUMN_VALUE_NAME)
            .appendPath(name)
            .build()

        log("- Querying '$configUri'.")
        val cursor = context.contentResolver.query(
            configUri,
            COLUMNS_PROJECTION,
            null,
            null,
            null
        )

        return if (cursor == null) {
            log("- Failed: Returning default. (Is the Jarvis app installed?)")
            lazyDefaultValue().toString()
        } else {
            log("- Success: Reading config value.")

            var configValue: String? = null

            try {
                cursor.use {
                    if (it.moveToFirst() && !it.isNull(COLUMN_VALUE_INDEX)) {
                        configValue = it.getString(COLUMN_VALUE_INDEX)
                    }
                }
            } catch (error: Throwable) {
                log("- Error: ${error.message}")
            }

            configValue ?: run {
                log("- Failed: Returning default.")
                lazyDefaultValue().toString()
            }
        }
    }

    private fun writeConfigToFile(config: JarvisConfig): File {

        val destinationFile = File(context.cacheDir, JARVIS_CONFIG_JSON_FILE).also {
            it.delete()
        }

        val configInputStream = jsonMapper.mapToJsonString(config).byteInputStream()
        val destinationOutputStream = FileOutputStream(destinationFile)

        configInputStream.use {
            destinationOutputStream.use {
                var read: Int
                val buff = ByteArray(1024)

                while (configInputStream.read(buff).also { read = it } > 0) {
                    destinationOutputStream.write(buff, 0, read)
                }
            }
        }

        return destinationFile
    }

    private fun log(message: String) {
        if (loggingEnabled) {
            Log.d(TAG, message)
        }
    }

    companion object {

        private const val TAG = "[JARVIS CLIENT]"

        private const val PACKAGE = "com.jarvis.app"
        private const val JARVIS_CONFIG_FILE_URI = "jarvis_config_file_uri"
        private const val JARVIS_CONFIG_PROVIDER = "jarvis_config_provider"

        private const val JARVIS_CONFIG = "jarvis_config"
        private const val JARVIS_CONFIG_JSON_FILE = "$JARVIS_CONFIG.json"

        private const val COLUMN_VALUE_NAME = "value"
        private const val COLUMN_VALUE_INDEX = 0

        private val CONFIG_BASE_URI = Uri.parse("content://$PACKAGE")

        private val COLUMNS_PROJECTION = arrayOf(COLUMN_VALUE_NAME)

        private const val JARVIS_PUSH_CONFIG_RESULT_SUCCESS = 0
        private const val JARVIS_PUSH_CONFIG_RESULT_FAILURE = 1
        private const val JARVIS_PUSH_CONFIG_RESULT_LOCKED = -1
        private const val JARVIS_PUSH_CONFIG_RESULT_CURSOR_ERROR = -2

        fun newInstance(context: Context): JarvisClient =
            JarvisClient(context, ClientJsonMapper())
    }
}
