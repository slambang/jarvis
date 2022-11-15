package com.jarvis.client

import android.content.Context
import com.jarvis.client.data.ClientJsonMapper
import com.jarvis.client.data.JarvisConfig
import com.jarvis.client.data.jarvisConfig

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
     * Enable/disable Jarvis's internal logging.
     * Useful for debugging purposes.
     */
    var loggingEnabled: Boolean

    /**
     * Pushes your app's Jarvis config to the Jarvis App.
     * Note that the Jarvis App must be in an *unlocked* state to accept new configs.
     *
     * @return [JarvisPushConfigResult] indicating the push operation result.
     */
    fun pushConfigToJarvisApp(config: JarvisConfig): JarvisPushConfigResult

    /**
     * Gets a named String value from the Jarvis App.
     *
     * @return The named String, or the default value if the Jarvis App is not installed
     * @param name The name of the string
     * @param defaultValue The default value
     */
    fun getString(name: String, defaultValue: String): String

    /**
     * Gets a named String value from the Jarvis App.
     *
     * @return The named String, or the default value if the Jarvis App is not installed
     * @param name The name of the string
     * @param lazyDefaultValue The lazily created default value
     */
    fun getString(name: String, lazyDefaultValue: () -> String): String

    /**
     * Gets a named Boolean value from the Jarvis App.
     *
     * @return The named Boolean, or the default value if the Jarvis App is not installed
     * @param name The name of the boolean
     * @param defaultValue The default value
     */
    fun getBoolean(name: String, defaultValue: Boolean): Boolean

    /**
     * Gets a named Boolean value from the Jarvis App.
     *
     * @return The named Boolean, or the default value if the Jarvis App is not installed
     * @param name The name of the boolean
     * @param lazyDefaultValue The lazily created default value
     */
    fun getBoolean(name: String, lazyDefaultValue: () -> Boolean): Boolean

    /**
     * Gets a named Long value from the Jarvis App.
     *
     * @return The named Long, or the default value if the Jarvis App is not installed
     * @param name The name of the long
     * @param defaultValue The default value
     */
    fun getLong(name: String, defaultValue: Long): Long

    /**
     * Gets a named Long value from the Jarvis App.
     *
     * @return The named Long, or the default value if the Jarvis App is not installed
     * @param name The name of the long
     * @param lazyDefaultValue The lazily created default value
     */
    fun getLong(name: String, lazyDefaultValue: () -> Long): Long

    /**
     * Gets a named Double value from the Jarvis App.
     *
     * @return The named Double, or the default value if the Jarvis App is not installed
     * @param name The name of the double
     * @param defaultValue The default value
     */
    fun getDouble(name: String, defaultValue: Double): Double

    /**
     * Gets a named Double value from the Jarvis App.
     *
     * @return The named Double, or the default value if the Jarvis App is not installed
     * @param name The name of the double
     * @param lazyDefaultValue The lazily created default value
     */
    fun getDouble(name: String, lazyDefaultValue: () -> Double): Double

    /**
     * Gets the index selection from a String list from the Jarvis App.
     *
     * @return The named String List selection index, or the default value if the Jarvis App is not installed
     * @param name The name of the string list
     * @param defaultValue The default selection index
     */
    fun getStringListSelection(name: String, defaultValue: Int): Int

    /**
     * Gets the index selection from a String list from the Jarvis App.
     *
     * @return The named String List selection index, or the default value if the Jarvis App is not installed
     * @param name The name of the String list
     * @param lazyDefaultValue The lazily created default value
     */
    fun getStringListSelection(name: String, lazyDefaultValue: () -> Int): Int

    companion object {
        /**
         * Creates a new instance of [JarvisClient].
         *
         * @return a new instance of [JarvisClient].
         */
        fun newInstance(context: Context): JarvisClient =
            JarvisClientImpl(context.applicationContext, ClientJsonMapper())
    }
}
