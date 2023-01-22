package com.jarvis.demo.advanced.repository

import android.content.Context
import com.jarvis.client.JarvisClient
import com.jarvis.client.data.builders.jarvisConfig
import com.jarvis.demo.advanced.ConfigRepository

/**
 * This file is only seen by the debug build variant
 */

fun injectRepository(context: Context): ConfigRepository =
    DebugConfigRepository(
        JarvisClient.newInstance(context)
    )

class DebugConfigRepository(
    private val jarvis: JarvisClient
) : ConfigRepository {

    init {
        /**
         * Step 1: Declare your app's config
         */
        val config = jarvisConfig {

            lockAfterPush = true

            withGroup {
                name = "My config group"
                isCollapsable = true
                startCollapsed = false

                withStringField {
                    name = STRING_FIELD_NAME
                    value = "Config value"
                }
            }
        }

        /**
         * Step 2: Push the config to the Jarvis App
         */
        jarvis.pushConfigToJarvisApp(config)
    }

    /**
     * Step 3: Returns the debug string value
     */
    override fun getStringValue(): String =
        jarvis.getString(STRING_FIELD_NAME, "Default value")

    companion object {
        private const val STRING_FIELD_NAME = "String field (advanced demo)"
    }
}
