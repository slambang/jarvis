@file:Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate", "UNUSED")

package com.jarvis.client.data

import java.lang.IllegalStateException

@DslMarker
annotation class JarvisDsl

fun jarvisConfig(block: JarvisConfigBuilder.() -> Unit): JarvisConfig =
    JarvisConfigBuilder().apply(block).build()

@JarvisDsl
class JarvisConfigBuilder {

    var withLockAfterPush = true

    private val fields = mutableListOf<JarvisField<Any>>()

    fun withStringField(block: StringFieldBuilder.() -> Unit) {
        addField(StringFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    fun withLongField(block: LongFieldBuilder.() -> Unit) {
        addField(LongFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    fun withDoubleField(block: DoubleFieldBuilder.() -> Unit) {
        addField(DoubleFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    fun withBooleanField(block: BooleanFieldBuilder.() -> Unit) {
        addField(BooleanFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    fun withStringListField(block: StringListFieldBuilder.() -> Unit) {
        addField(StringListFieldBuilder().apply(block).build() as JarvisField<Any>)
    }

    fun build(): JarvisConfig =
        JarvisConfig(withLockAfterPush, fields.toList())

    private fun addField(field: JarvisField<Any>) {
        fields.find { it.name == field.name }?.let {
            throw IllegalStateException("Duplicate Jarvis field name `${it.name}`.")
        }
        fields.add(field)
    }
}

abstract class BaseFieldBuilder<T> {
    lateinit var name: String
    var value: T? = null
    var description: String? = null
}

@JarvisDsl
class StringFieldBuilder : BaseFieldBuilder<String>() {

    var hint: String? = null
    var minLength: Long = 0
    var maxLength: Long = 999
    var regex: String? = null

    fun build(): JarvisField<String> =
        StringField(
            StringField::class.java.simpleName,
            name,
            value!!,
            value!!,
            description,
            isPublished = true,
            hint,
            minLength,
            maxLength,
            regex
        )
}

@JarvisDsl
class LongFieldBuilder : BaseFieldBuilder<Long>() {

    var hint: String? = null
    var min: Long? = null
    var max: Long? = null
    var asRange: Boolean = false

    fun build(): LongField =
        LongField(
            LongField::class.java.simpleName,
            name,
            value!!,
            value!!,
            description,
            isPublished = true,
            hint,
            min,
            max,
            asRange
        )
}

@JarvisDsl
class DoubleFieldBuilder : BaseFieldBuilder<Double>() {

    var hint: String? = null
    var min: Double? = null
    var max: Double? = null
    var asRange: Boolean = false

    fun build(): DoubleField =
        DoubleField(
            DoubleField::class.java.simpleName,
            name,
            value!!,
            value!!,
            description,
            isPublished = true,
            hint,
            min,
            max,
            asRange
        )
}

@JarvisDsl
class BooleanFieldBuilder : BaseFieldBuilder<Boolean>() {

    fun build(): BooleanField =
        BooleanField(
            BooleanField::class.java.simpleName,
            name,
            value!!,
            value!!,
            description,
            isPublished = true
        )
}

@JarvisDsl
class StringListFieldBuilder : BaseFieldBuilder<List<String>>() {

    var defaultSelection = 0

    fun build(): StringListField =
        StringListField(
            StringListField::class.java.simpleName,
            name,
            value!!,
            value!!,
            description,
            isPublished = true,
            defaultSelection,
            defaultSelection
        )
}
