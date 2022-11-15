package com.jarvis.demo.advanced.repository

import com.jarvis.client.JarvisClient
import com.jarvis.client.data.jarvisConfig

/**
 * Only seen by the `debug` build variant.
 */
class DebugConfigRepository(
    private val jarvis: JarvisClient,
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : ConfigRepository {

    init {
        /**
         * Ensure the config is pushed to the Jarvis App before trying to read values.
         */
        with (jarvis) {
            loggingEnabled = true
            pushConfigToJarvisApp(JARVIS_CONFIG)
        }
    }

    /**
     * Retrieves the value from the Jarvis App (if installed).
     * Falls back to [FirebaseRemoteConfig] for the default value.
     */
    override fun someStringValue(): String =
        jarvis.getString(SOME_STRING_NAME, firebaseRemoteConfig::someStringValue)

    companion object {

        private const val SOME_STRING_NAME = "Some string (advanced demo)"

        private val JARVIS_CONFIG = jarvisConfig {

            withLockAfterPush = false

            withStringField {
                name = SOME_STRING_NAME
                value = "Jarvis value"
                maxLength = 12
            }
        }
    }
}
