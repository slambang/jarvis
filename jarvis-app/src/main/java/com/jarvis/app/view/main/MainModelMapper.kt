package com.jarvis.app.view.main

import com.jarvis.app.R
import com.jarvis.app.data.JarvisAppSettings
import com.jarvis.app.view.util.ResourceProvider
import com.jarvis.client.data.*
import javax.inject.Inject

class MainModelMapper @Inject constructor(
    private val validatorMapper: ValidatorMapper,
    private val resources: ResourceProvider
) {
    fun mapMenuModel(appSettingsDomain: JarvisAppSettings): MainMenuViewModel {

        val lockedTextId = when (appSettingsDomain.isJarvisLocked) {
            true -> R.string.toolbar_subtitle_locked
            false -> R.string.toolbar_subtitle_unlocked
        }

        val activeTextId = when (appSettingsDomain.isJarvisActive) {
            true -> R.string.toolbar_subtitle_active
            false -> R.string.toolbar_subtitle_inactive
        }

        val toolbarSubtitle =
            resources.getString(
                R.string.toolbar_subtitle_template,
                resources.getString(activeTextId),
                resources.getString(lockedTextId)
            )

        return MainMenuViewModel(
            toolbarSubtitle,
            appSettingsDomain.isJarvisActive,
            appSettingsDomain.isJarvisLocked
        )
    }

    fun mapToConfigItems(domains: List<JarvisConfigGroup>): List<ConfigGroupItemViewModel> =
        domains.map { groupDomain ->
            ConfigGroupItemViewModel(
                name = groupDomain.name,
                isCollapsable = groupDomain.isCollapsable,
                isCollapsed = groupDomain.startCollapsed,
                fields = (groupDomain.fields as List<JarvisField<*>>).map { fieldDomain ->
                    when (fieldDomain) {
                        is StringField -> mapStringField(fieldDomain)
                        is LongField -> mapLongField(fieldDomain)
                        is DoubleField -> mapDoubleField(fieldDomain)
                        is BooleanField -> mapBooleanField(fieldDomain)
                        is StringListField -> mapStringListField(fieldDomain)
                    }
                }
            )
        }

    private fun mapStringField(field: StringField): StringFieldItemViewModel =
        StringFieldItemViewModel(
            field.hint,
            field.name,
            field.description,
            field.value,
            field.defaultValue,
            field.isPublished,
            field,
            validatorMapper.getStringFieldValidator(field)
        )

    private fun mapLongField(field: LongField): LongFieldItemViewModel =
        LongFieldItemViewModel(
            field.hint,
            field.asRange,
            field.name,
            field.description,
            field.value,
            field.defaultValue,
            field.isPublished,
            field,
            validatorMapper.getLongFieldValidator(field)
        )

    private fun mapDoubleField(field: DoubleField): DoubleFieldItemViewModel =
        DoubleFieldItemViewModel(
            field.hint,
            field.asRange,
            field.name,
            field.description,
            field.value,
            field.defaultValue,
            field.isPublished,
            field,
            validatorMapper.getDoubleFieldValidator(field)
        )

    private fun mapBooleanField(field: BooleanField): BooleanFieldItemViewModel =
        BooleanFieldItemViewModel(
            field.name,
            field.description,
            field.value,
            field.defaultValue,
            field.isPublished,
            field,
            validatorMapper.getBooleanFieldValidator(field)
        )

    private fun mapStringListField(field: StringListField): StringListFieldItemViewModel =
        StringListFieldItemViewModel(
            field.defaultSelection,
            field.currentSelection,
            field.name,
            field.description,
            field.value,
            field.defaultValue,
            field.isPublished,
            field,
            validatorMapper.getStringListFieldValidator(field)
        )
}
