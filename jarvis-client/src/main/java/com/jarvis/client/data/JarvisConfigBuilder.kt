@file:Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate", "UNUSED")

package com.jarvis.client.data

import com.jarvis.client.JarvisClient
import java.lang.IllegalStateException

/**
 * Creates a [JarvisConfigBuilder] and calls the function block on it.
 * Once the function block returns [JarvisConfigBuilder.build] is called.
 */
fun jarvisConfig(block: JarvisConfigBuilder.() -> Unit): JarvisConfig =
    JarvisConfigBuilder().apply(block).build()

/**
 * Builder pattern to create a [JarvisConfig].
 * Use this class to add different config field types.
 */
@JarvisDsl
class JarvisConfigBuilder {

    /**
     * Optionally locks Jarvis after a config is pushed.
     *
     * Jarvis must be *unlocked* to accept a new config being pushed.
     * If this value is true then Jarvis will automatically lock after receiving a new config.
     * If this value is false then Jarvis will remain unlocked after receiving a new config.
     */
    var withLockAfterPush = true

    private val fields = mutableListOf<JarvisField<Any>>()

    /**
     * Adds a String field to the config.
     *
     * Creates a [StringFieldBuilder] and calls the function block on it.
     */
    fun withStringField(block: StringFieldBuilder.() -> Unit) {
        addField(StringFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    /**
     * Adds a Long field to the config.
     *
     * Creates a [LongFieldBuilder] and calls the function block on it.
     */
    fun withLongField(block: LongFieldBuilder.() -> Unit) {
        addField(LongFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    /**
     * Adds a Double field to the config.
     *
     * Creates a [DoubleFieldBuilder] and calls the function block on it.
     */
    fun withDoubleField(block: DoubleFieldBuilder.() -> Unit) {
        addField(DoubleFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    /**
     * Adds a Boolean field to the config.
     *
     * Creates a [BooleanFieldBuilder] and calls the function block on it.
     */
    fun withBooleanField(block: BooleanFieldBuilder.() -> Unit) {
        addField(BooleanFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    /**
     * Adds a String List field to the config.
     *
     * Creates a [StringListFieldBuilder] and calls the function block on it.
     */
    fun withStringListField(block: StringListFieldBuilder.() -> Unit) {
        addField(StringListFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    /**
     * Returns the Jarvis config according to the fields that have been set.
     *
     * @return [JarvisConfig] to (be passed to [JarvisClient.pushConfigToJarvisApp]).
     */
    fun build(): JarvisConfig =
        JarvisConfig(withLockAfterPush, fields.toList())

    private fun addField(field: JarvisField<Any>) {
        fields.find { it.name == field.name }?.let {
            throw IllegalStateException("Duplicate Jarvis field name `${it.name}`.")
        }
        fields.add(field)
    }
}
