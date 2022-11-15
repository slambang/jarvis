package com.jarvis.app.contentprovider

import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.jarvis.app.BuildConfig
import com.jarvis.app.contentprovider.util.ReadOnlyContentProvider
import com.jarvis.app.di.JarvisContentProviderEntryPoint
import com.jarvis.app.domain.fields.JarvisContentProviderViewModel
import java.lang.RuntimeException

class JarvisContentProvider : ReadOnlyContentProvider() {

    private val viewModel: JarvisContentProviderViewModel by lazy {
        JarvisContentProviderEntryPoint.getViewModel(context!!)
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        log("URI received: `$uri`.")

        val returnValue = when {
            isRefreshConfigUri(uri) -> onConfigLocationReceived(uri).toString()
            isReadFieldUri(uri) -> onReadRequestReceived(uri)
            else -> onUnexpectedUriReceived(uri)
        }

        return JarvisCursor(returnValue)
    }

    private fun onConfigLocationReceived(uri: Uri): Int {
        log("- Config file URI received: $uri")

        if (viewModel.isJarvisLocked) {
            log("- Jarvis is locked. Skipping.")
            return JARVIS_PULL_CONFIG_RESULT_LOCKED
        }

        try {
            log("- Pulling config file.")

            val jarvisConfigFilePath = uri.getQueryParameter(JARVIS_CONFIG_FILE_URI)
                ?: throw RuntimeException("Failed to get config file URI.")

            val jarvisConfigFileUri = Uri.parse(jarvisConfigFilePath)

            val stream = context!!.contentResolver.openInputStream(jarvisConfigFileUri)
                ?: throw RuntimeException("Failed to open config input stream.")

            stream.use {
                viewModel.onConfigPush(it)
            }

            return JARVIS_PULL_CONFIG_RESULT_SUCCESS
        } catch (error: Throwable) {
            log("- Error: ${error.message}")
            return JARVIS_PULL_CONFIG_RESULT_FAILURE
        }
    }

    private fun onReadRequestReceived(uri: Uri): String? {
        log("- Reading value '${uri.pathSegments[PATH_CONFIG_VALUE_INDEX]}'.")
        val configValue = readFieldValue(uri)
        log("- Success. Returning '$configValue'.")
        return configValue
    }

    private fun onUnexpectedUriReceived(uri: Uri): String? {
        log("- Unknown uri: $uri.")
        return null
    }

    private fun isRefreshConfigUri(uri: Uri): Boolean =
        uri.getQueryParameter(JARVIS_CONFIG_FILE_URI) != null

    private fun isReadFieldUri(uri: Uri): Boolean =
        uri.authority == BuildConfig.JARVIS_CONFIG_AUTHORITY &&
                uri.pathSegments.size == 2 &&
                uri.pathSegments[PATH_VALUE_INDEX] == PATH_VALUE

    private fun readFieldValue(uri: Uri): String? {
        if (!viewModel.isJarvisActive) return null
        val fieldName = uri.pathSegments[PATH_CONFIG_VALUE_INDEX]
        return viewModel.getFieldValue(fieldName)
    }

    private fun log(message: String) {
        Log.d(TAG, "APP: $message")
    }

    companion object {
        private const val TAG = "[JARVIS]"

        private const val PATH_VALUE = "value"
        private const val JARVIS_CONFIG_FILE_URI = "jarvis_config_file_uri"

        private const val PATH_VALUE_INDEX = 0
        private const val PATH_CONFIG_VALUE_INDEX = 1

        private const val JARVIS_PULL_CONFIG_RESULT_SUCCESS = 0
        private const val JARVIS_PULL_CONFIG_RESULT_FAILURE = 1
        private const val JARVIS_PULL_CONFIG_RESULT_LOCKED = -1
    }
}
