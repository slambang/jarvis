@file:Suppress("MemberVisibilityCanBePrivate", "Unused", "UNCHECKED_CAST")

package com.jarvis.client.data.builders

import com.jarvis.client.data.JarvisConfigGroup
import com.jarvis.client.data.JarvisField
import java.lang.IllegalStateException

/**
 * Builder to create a [JarvisConfigGroup].
 * Use this class to configure individual config groups.
 */
class GroupBuilder {

    /**
     * The name of the group.
     * Must be unique.
     */
    var name: String? = null

    /**
     * Enables/disables the group's collapsibility in the Jarvis App's UI.
     */
    var isCollapsable: Boolean = true

    /**
     * Determines if the group should be rendered collapsed or expanded by default.
     * Ignored if [isCollapsable] is false.
     */
    var startCollapsed: Boolean = true

    private val fields = mutableListOf<JarvisField<Any>>()

    /**
     * Adds a String field to the config.
     *
     * Creates a [StringFieldBuilder] and calls the function block on it.
     */
    fun withStringField(block: StringFieldBuilder.() -> Unit): Unit =
        addField(StringFieldBuilder().apply(block).build() as JarvisField<Any>)

    /**
     * Adds a Long field to the config.
     *
     * Creates a [LongFieldBuilder] and calls the function block on it.
     */
    fun withLongField(block: LongFieldBuilder.() -> Unit): Unit =
        addField(LongFieldBuilder().apply(block).build() as JarvisField<Any>)

    /**
     * Adds a Double field to the config.
     *
     * Creates a [DoubleFieldBuilder] and calls the function block on it.
     */
    fun withDoubleField(block: DoubleFieldBuilder.() -> Unit): Unit =
        addField(DoubleFieldBuilder().apply(block).build() as JarvisField<Any>)

    /**
     * Adds a Boolean field to the config.
     *
     * Creates a [BooleanFieldBuilder] and calls the function block on it.
     */
    fun withBooleanField(block: BooleanFieldBuilder.() -> Unit): Unit =
        addField(BooleanFieldBuilder().apply(block).build() as JarvisField<Any>)

    /**
     * Adds a String List field to the config.
     *
     * Creates a [StringListFieldBuilder] and calls the function block on it.
     */
    fun withStringListField(block: StringListFieldBuilder.() -> Unit): Unit =
        addField(StringListFieldBuilder().apply(block).build() as JarvisField<Any>)

    internal fun build(): JarvisConfigGroup =
        when (name) {
            null -> throw IllegalArgumentException("All Jarvis groups must have a name.")
            else -> JarvisConfigGroup(name!!, isCollapsable, startCollapsed, fields)
        }

    private fun addField(field: JarvisField<Any>) {
        fields.find { it.name == field.name }?.let {
            throw IllegalStateException("Duplicate Jarvis field name `${it.name}`.")
        }
        fields.add(field)
    }
}
