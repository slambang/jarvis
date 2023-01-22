package com.jarvis.client

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.jarvis.client.data.JarvisConfig
import com.jarvis.client.data.toJson
import java.io.File
import java.io.FileOutputStream
import java.lang.IllegalArgumentException
import java.lang.ref.WeakReference

internal class JarvisClientImpl(context: Context): JarvisClient {

    override var loggingEnabled = true

    private val _contextRef = WeakReference(context.applicationContext)
    private val context: Context
        get() = _contextRef.get()!! // Should be safe because context.applicationContext above

    @Synchronized
    override fun pushConfigToJarvisApp(config: JarvisConfig): JarvisPushConfigResult {

        log("Pushing config to the Jarvis App.")
        val jarvisConfigFile = config.writeToFile()
        log("- Writing Jarvis config to local file `$jarvisConfigFile`.")

        val jarvisConfigFileUri = try {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.jarvis_config_provider",
                jarvisConfigFile
            )
        } catch (error: IllegalArgumentException) {
            log("- Error: ${error.message}. Have you added the <provider> to your app's manifest?")
            return JarvisPushConfigResult.FAILURE
        }

        log("- Granting Jarvis App permission for URI '$jarvisConfigFileUri' from package '${context.packageName}'.")
        context.grantUriPermission(
            JARVIS_APP_PACKAGE,
            jarvisConfigFileUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        val success: Int = try {
            val configUri = CONFIG_BASE_URI.buildUpon()
                .appendQueryParameter("jarvis_config_file_uri", jarvisConfigFileUri.toString())
                .build()

            log("- Pushing config file URI to Jarvis App.")
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

        val pushConfigResult = when (success) {
            JARVIS_PUSH_CONFIG_RESULT_SUCCESS -> JarvisPushConfigResult.SUCCESS
            JARVIS_PUSH_CONFIG_RESULT_LOCKED -> JarvisPushConfigResult.LOCKED
            else -> JarvisPushConfigResult.FAILURE
        }

        val message = when (pushConfigResult) {
            JarvisPushConfigResult.SUCCESS -> "Success"
            JarvisPushConfigResult.FAILURE -> "Failure (is the Jarvis App installed?)"
            JarvisPushConfigResult.LOCKED -> "Failure: Jarvis App is locked"
        }
        log("Jarvis push: $message")

        return pushConfigResult
    }

    override fun getString(name: String, defaultValue: String): String =
        getString(name) { defaultValue }

    override fun getString(name: String, lazyDefaultValue: () -> String): String =
        safeGetConfigValue(name, lazyDefaultValue, String::toString)

    override fun getBoolean(name: String, defaultValue: Boolean): Boolean =
        getBoolean(name) { defaultValue }

    override fun getBoolean(name: String, lazyDefaultValue: () -> Boolean): Boolean =
        safeGetConfigValue(name, lazyDefaultValue, String::toBoolean)

    override fun getLong(name: String, defaultValue: Long): Long =
        getLong(name) { defaultValue }

    override fun getLong(name: String, lazyDefaultValue: () -> Long): Long =
        safeGetConfigValue(name, lazyDefaultValue, String::toLong)

    override fun getDouble(name: String, defaultValue: Double): Double =
        getDouble(name) { defaultValue }

    override fun getDouble(name: String, lazyDefaultValue: () -> Double): Double =
        safeGetConfigValue(name, lazyDefaultValue, String::toDouble)

    override fun getStringListSelection(name: String, defaultValue: Int): Int =
        getStringListSelection(name) { defaultValue }

    override fun getStringListSelection(name: String, lazyDefaultValue: () -> Int): Int =
        safeGetConfigValue(name, lazyDefaultValue, String::toInt)

    @Synchronized
    private fun <T : Any> safeGetConfigValue(
        name: String,
        lazyDefaultValue: () -> T,
        toDataType: String.() -> T
    ): T = try {
        log("Reading '$name'.")
        getConfigValue(name, lazyDefaultValue).toDataType().also {
            log("- Success: Returning value '$it'.")
        }
    } catch (error: Throwable) {
        lazyDefaultValue().also {
            log("- Failed: $error. Returning default value '$it'.")
        }
    }

    private fun <T : Any> getConfigValue(
        name: String,
        lazyDefaultValue: () -> T
    ): String {
        val configUri = CONFIG_BASE_URI.buildUpon()
            .appendEncodedPath(COLUMN_VALUE_NAME)
            .appendPath(name)
            .build()

        log("- Querying: $configUri.")
        val cursor = context.contentResolver.query(
            configUri,
            arrayOf(COLUMN_VALUE_NAME),
            null,
            null,
            null
        )

        return if (cursor == null) {
            log("- Failed: Returning default. (Is the Jarvis App installed?)")
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
                log("- Failed: Returning default value.")
                lazyDefaultValue().toString()
            }
        }
    }

    private fun JarvisConfig.writeToFile(): File {

        val destinationFile = File(context.cacheDir, "jarvis_config.json").also {
            it.delete()
        }

        val fileOutputStream = FileOutputStream(destinationFile)
        val configInputStream = this.toJson().byteInputStream()
        configInputStream.copyTo(fileOutputStream)

        return destinationFile
    }

    private fun log(message: String) {
        if (loggingEnabled) {
            Log.d("[JARVIS]", "CLIENT: $message")
        }
    }

    companion object {
        private const val JARVIS_APP_PACKAGE = "com.jarvis.app"
        private const val COLUMN_VALUE_NAME = "value"
        private const val COLUMN_VALUE_INDEX = 0
        private val CONFIG_BASE_URI = Uri.parse("content://$JARVIS_APP_PACKAGE")

        private const val JARVIS_PUSH_CONFIG_RESULT_SUCCESS = 0
        private const val JARVIS_PUSH_CONFIG_RESULT_FAILURE = 1
        private const val JARVIS_PUSH_CONFIG_RESULT_LOCKED = -1
        private const val JARVIS_PUSH_CONFIG_RESULT_CURSOR_ERROR = -2
    }
}
