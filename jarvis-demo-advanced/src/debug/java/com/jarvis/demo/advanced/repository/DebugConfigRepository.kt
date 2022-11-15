package com.jarvis.demo.advanced.repository

import android.content.Context
import com.jarvis.client.JarvisClient
import com.jarvis.client.data.jarvisConfig
import com.jarvis.demo.advanced.ConfigRepository

fun getRepository(context: Context): ConfigRepository =
    DebugConfigRepository(
        JarvisClient.newInstance(context)
    )

/**
 * Only seen by the `debug` build variant.
 */
class DebugConfigRepository(
    private val jarvis: JarvisClient
) : ConfigRepository {

    init {
        /**
         * Step 1: Declare the Jarvis config.
         */
        val config = jarvisConfig {

            withLockAfterPush = true

            withStringField {
                name = STRING_FIELD_NAME
                value = "Jarvis value"
            }
        }

        /**
         * Step 2: Push the config to the Jarvis App.
         */
        with (jarvis) {
            loggingEnabled = true
            pushConfigToJarvisApp(config)
        }
    }

    /**
     * Step 3: Read the value.
     */
    override fun getStringValue(): String =
        jarvis.getString(STRING_FIELD_NAME, "Debug value")

    companion object {
        private const val STRING_FIELD_NAME = "String field (advanced demo)"
    }
}
