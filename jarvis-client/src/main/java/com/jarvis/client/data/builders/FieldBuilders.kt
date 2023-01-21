@file:Suppress("MemberVisibilityCanBePrivate")

package com.jarvis.client.data.builders

import com.jarvis.client.data.*

abstract class BaseFieldBuilder<T> {

    /**
     * The name of the field.
     * Must be unique per [JarvisConfigGroup].
     */
    lateinit var name: String

    /**
     * The value of the field.
     */

    var value: T? = null

    /**
     * The user-friendly description of the field.
     */
    var description: String? = null
}

class StringFieldBuilder : BaseFieldBuilder<String>() {

    /**
     * The user-friendly hint displayed in the EditText.
     */
    var hint: String? = null

    /**
     * Validation: the minimum required length of the String.
     */
    var minLength: Long = 0

    /**
     * Validation: the maximum required length of the String.
     */
    var maxLength: Long = 999

    /**
     * Validation: the regex to be applied to the String.
     */
    var regex: String? = null

    internal fun build(): JarvisField<String> =
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

class LongFieldBuilder : BaseFieldBuilder<Long>() {

    /**
     * The user-friendly hint displayed in the EditText.
     */
    var hint: String? = null

    /**
     * Validation: the minimum Long vale.
     */
    var min: Long? = null

    /**
     * Validation: the maximum Long vale.
     */
    var max: Long? = null

    /**
     * TODO
     */
    var asRange: Boolean = false

    internal fun build(): LongField =
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

class DoubleFieldBuilder : BaseFieldBuilder<Double>() {

    /**
     * The user-friendly hint displayed in the EditText.
     */
    var hint: String? = null

    /**
     * Validation: the minimum Double vale.
     */
    var min: Double? = null

    /**
     * Validation: the maximum Double vale.
     */
    var max: Double? = null

    /**
     * TODO
     */
    var asRange: Boolean = false

    internal fun build(): DoubleField =
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

class BooleanFieldBuilder : BaseFieldBuilder<Boolean>() {

    internal fun build(): BooleanField =
        BooleanField(
            BooleanField::class.java.simpleName,
            name,
            value!!,
            value!!,
            description,
            isPublished = true
        )
}

class StringListFieldBuilder : BaseFieldBuilder<List<String>>() {

    /**
     * The default selection index within the String List.
     */
    var defaultSelection = 0

    internal fun build(): StringListField =
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
