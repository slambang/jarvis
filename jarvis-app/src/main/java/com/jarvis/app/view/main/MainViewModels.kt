package com.jarvis.app.view.main

import com.jarvis.client.data.JarvisField

data class MainMenuViewModel(
    val toolbarSubtitle: String,
    val isActive: Boolean,
    val isLocked: Boolean
)

data class ConfigGroupItemViewModel(
    val name: String,
    val isCollapsable: Boolean,
    var isCollapsed: Boolean,
    val fields: List<FieldItemViewModel<*>>
)

sealed class FieldItemViewModel<T : Any> {
    abstract val name: String
    abstract val description: String?
    abstract val value: T
    abstract val defaultValue: T
    abstract val isPublished: Boolean
    abstract val fieldDomain: JarvisField<*>
    abstract val validate: (T) -> String?
}

data class StringFieldItemViewModel(
    val hint: String?,
    override val name: String,
    override val description: String?,
    override val value: String,
    override val defaultValue: String,
    override val isPublished: Boolean,
    override val fieldDomain: JarvisField<*>,
    override val validate: (String) -> String?,
) : FieldItemViewModel<String>()

data class LongFieldItemViewModel(
    val hint: String?,
    val asRange: Boolean,
    override val name: String,
    override val description: String?,
    override val value: Long,
    override val defaultValue: Long,
    override val isPublished: Boolean,
    override val fieldDomain: JarvisField<*>,
    override val validate: (Long) -> String?
) : FieldItemViewModel<Long>()

data class DoubleFieldItemViewModel(
    val hint: String?,
    val asRange: Boolean,
    override val name: String,
    override val description: String?,
    override val value: Double,
    override val defaultValue: Double,
    override val isPublished: Boolean,
    override val fieldDomain: JarvisField<*>,
    override val validate: (Double) -> String?
) : FieldItemViewModel<Double>()

data class BooleanFieldItemViewModel(
    override val name: String,
    override val description: String?,
    override val value: Boolean,
    override val defaultValue: Boolean,
    override val isPublished: Boolean,
    override val fieldDomain: JarvisField<*>,
    override val validate: (Boolean) -> String?
) : FieldItemViewModel<Boolean>()

data class StringListFieldItemViewModel(
    val defaultSelection: Int,
    val currentSelection: Int,
    override val name: String,
    override val description: String?,
    override val value: List<String>,
    override val defaultValue: List<String>,
    override val isPublished: Boolean,
    override val fieldDomain: JarvisField<*>,
    override val validate: (List<String>) -> String?
) : FieldItemViewModel<List<String>>()
