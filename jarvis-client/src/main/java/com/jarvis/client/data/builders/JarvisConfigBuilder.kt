package com.jarvis.client.data.builders

import com.jarvis.client.JarvisClient
import com.jarvis.client.data.JarvisConfig
import com.jarvis.client.data.JarvisConfigGroup
import java.lang.IllegalStateException

/**
 * Creates a [JarvisConfigBuilder] and calls the function block on it.
 *
 * @return a new [JarvisConfig] instance
 */
fun jarvisConfig(block: JarvisConfigBuilder.() -> Unit): JarvisConfig =
    JarvisConfigBuilder().apply(block).build()

/**
 * Builder to create a [JarvisConfig].
 * @see [JarvisClient.pushConfigToJarvisApp]
 */
class JarvisConfigBuilder {

    /**
     * Optionally locks Jarvis after a config is pushed.
     *
     * Jarvis must be *unlocked* to accept a new config being pushed.
     * If this value is true then Jarvis will automatically lock after receiving a new config.
     * If this value is false then Jarvis will remain unlocked after receiving a new config.
     */
    var lockAfterPush = true

    private val groups = mutableListOf<JarvisConfigGroup>()

    /**
     * Adds a field group to the config.
     *
     * Creates a [GroupBuilder] and calls the function block on it.
     */
    fun withGroup(block: GroupBuilder.() -> Unit): Unit =
        addGroup(GroupBuilder().apply(block).build())

    internal fun build(): JarvisConfig =
        JarvisConfig(lockAfterPush, groups)

    private fun addGroup(group: JarvisConfigGroup) {
        groups.find { it.name == group.name }?.let {
            throw IllegalStateException("Duplicate group name `${it.name}`.")
        }
        groups.add(group)
    }
}
