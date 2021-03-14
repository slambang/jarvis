package com.jarvis.client.data

import androidx.annotation.Keep
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

// TODO Be consistent with <Any>
@Keep
@Serializable
data class JarvisConfig(
    var lockAfterPush: Boolean,
    val fields: List<JarvisField<@Polymorphic Any>>
)

// TODO How/Can we automatically get "type" discriminator?
@Keep
@Serializable(with = JarvisFieldSerializer::class)
sealed class JarvisField<T> {
    abstract val type: String
    abstract val name: String
    abstract val value: T
    abstract val defaultValue: T
    abstract val description: String?
    abstract val isPublished: Boolean
}

@Keep
@Serializable
data class StringField(
    override val type: String,
    override val name: String,
    override val value: String,
    override val defaultValue: String,
    override val description: String?,
    override val isPublished: Boolean,
    val hint: String?,
    val minLength: Long?,
    val maxLength: Long?,
    val regex: String?,
) : JarvisField<String>()

@Keep
@Serializable
data class LongField(
    override val type: String,
    override val name: String,
    override val value: Long,
    override val defaultValue: Long,
    override val description: String?,
    override val isPublished: Boolean,
    val hint: String?,
    val min: Long?,
    val max: Long?,
    val asRange: Boolean
) : JarvisField<Long>()

@Keep
@Serializable
data class DoubleField(
    override val type: String,
    override val name: String,
    override val value: Double,
    override val defaultValue: Double,
    override val description: String?,
    override val isPublished: Boolean,
    val hint: String?,
    val min: Double?,
    val max: Double?,
    val asRange: Boolean
) : JarvisField<Double>()

@Keep
@Serializable
data class BooleanField(
    override val type: String,
    override val name: String,
    override val value: Boolean,
    override val defaultValue: Boolean,
    override val description: String?,
    override val isPublished: Boolean,
) : JarvisField<Boolean>()

@Keep
@Serializable
data class StringListField(
    override val type: String,
    override val name: String,
    override val value: List<String>,
    override val defaultValue: List<String>,
    override val description: String?,
    override val isPublished: Boolean,
    val defaultSelection: Int,
    val currentSelection: Int
) : JarvisField<List<String>>()
