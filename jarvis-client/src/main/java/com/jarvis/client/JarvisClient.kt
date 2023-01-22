package com.jarvis.client

import android.content.Context
import com.jarvis.client.data.ClientJsonMapper
import com.jarvis.client.data.JarvisConfig
import com.jarvis.client.data.builders.jarvisConfig
import com.jarvis.client.data.builders.JarvisConfigBuilder

/**
 * The Jarvis interface, created via [JarvisClient.newInstance].
 *
 * 1. @see [jarvisConfig] to create a Jarvis config.
 * 2. Pass your Jarvis config to [JarvisClient.pushConfigToJarvisApp].
 * 3. Read data from the Jarvis App via the `get*()` functions.
 *
 * If the Jarvis App is installed then all `get*()` functions return data from the config there.
 * If the Jarvis App is not installed then the default values are returned.
 */
interface JarvisClient {

    /**
     * Enable/disable the JarvisClient internal logging.
     * Useful for debugging integration issues.
     */
    var loggingEnabled: Boolean

    /**
     * Pushes your app's Jarvis config to the Jarvis App.
     * Note that the Jarvis App must be in an *unlocked* state to accept new configs.
     * @see [JarvisConfigBuilder] to create a config.
     *
     * @return [JarvisPushConfigResult] indicating the push operation result
     */
    fun pushConfigToJarvisApp(config: JarvisConfig): JarvisPushConfigResult

    /**
     * Gets a named String value from the Jarvis App.
     *
     * @param name The name of the String
     * @param defaultValue The default value
     * @return The named String, or the default value if the Jarvis App is not installed
     */
    fun getString(name: String, defaultValue: String): String

    /**
     * Gets a named String value from the Jarvis App.
     *
     * @param name The name of the String
     * @param lazyDefaultValue The lazily created default value
     * @return The named String, or the default value if the Jarvis App is not installed
     */
    fun getString(name: String, lazyDefaultValue: () -> String): String

    /**
     * Gets a named Boolean value from the Jarvis App.
     *
     * @param name The name of the Boolean
     * @param defaultValue The default value
     * @return The named Boolean, or the default value if the Jarvis App is not installed
     */
    fun getBoolean(name: String, defaultValue: Boolean): Boolean

    /**
     * Gets a named Boolean value from the Jarvis App.
     *
     * @param name The name of the Boolean
     * @param lazyDefaultValue The lazily created default value
     * @return The named Boolean, or the default value if the Jarvis App is not installed
     */
    fun getBoolean(name: String, lazyDefaultValue: () -> Boolean): Boolean

    /**
     * Gets a named Long value from the Jarvis App.
     *
     * @param name The name of the Long
     * @param defaultValue The default value
     * @return The named Long, or the default value if the Jarvis App is not installed
     */
    fun getLong(name: String, defaultValue: Long): Long

    /**
     * Gets a named Long value from the Jarvis App.
     *
     * @param name The name of the Long
     * @param lazyDefaultValue The lazily created default value
     * @return The named Long, or the default value if the Jarvis App is not installed
     */
    fun getLong(name: String, lazyDefaultValue: () -> Long): Long

    /**
     * Gets a named Double value from the Jarvis App.
     *
     * @param name The name of the Double
     * @param defaultValue The default value
     * @return The named Double, or the default value if the Jarvis App is not installed
     */
    fun getDouble(name: String, defaultValue: Double): Double

    /**
     * Gets a named Double value from the Jarvis App.
     *
     * @param name The name of the Double
     * @param lazyDefaultValue The lazily created default value
     * @return The named Double, or the default value if the Jarvis App is not installed
     */
    fun getDouble(name: String, lazyDefaultValue: () -> Double): Double

    /**
     * Gets the index selection from a String list from the Jarvis App.
     *
     * @param name The name of the String List
     * @param defaultValue The default selection index
     * @return The named String List selection index, or the default value if the Jarvis App is not installed
     */
    fun getStringListSelection(name: String, defaultValue: Int): Int

    /**
     * Gets the index selection from a String list from the Jarvis App.
     *
     * @param name The name of the String List
     * @param lazyDefaultValue The lazily created default value
     * @return The named String List selection index, or the default value if the Jarvis App is not installed
     */
    fun getStringListSelection(name: String, lazyDefaultValue: () -> Int): Int

    companion object {

        /**
         * @return a new instance of [JarvisClient].
         */
        @JvmStatic
        fun newInstance(context: Context): JarvisClient =
            JarvisClientImpl(context.applicationContext, ClientJsonMapper())
    }
}
