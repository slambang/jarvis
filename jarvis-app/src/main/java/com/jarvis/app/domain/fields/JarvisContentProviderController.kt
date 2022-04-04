package com.jarvis.app.domain.fields

import com.jarvis.app.domain.interactors.FieldsInteractor
import com.jarvis.app.domain.interactors.SettingsInteractor
import com.jarvis.client.data.StringListField
import java.io.InputStream
import javax.inject.Inject

class JarvisContentProviderController @Inject constructor(
    private val fieldsInteractor: FieldsInteractor,
    private val settingsInteractor: SettingsInteractor
) {
    val isJarvisLocked: Boolean
        get() = settingsInteractor.isJarvisLocked

    val isJarvisActive: Boolean
        get() = settingsInteractor.isJarvisActive

    fun onConfigPush(inputStream: InputStream) {
        fieldsInteractor.refreshConfig(inputStream).also {
            settingsInteractor.isJarvisLocked = it.lockAfterPush
        }
    }

    fun getFieldValue(fieldName: String): String? {
        val field = fieldsInteractor.getJarvisField(fieldName)
        return when (field?.isPublished == true) {
            true -> {
                when (field!!::class) {
                    StringListField::class -> {
                        field as StringListField
                        field.currentSelection.toString()
                    }
                    else -> field.value.toString()
                }
            }
            false -> null
        }
    }
}
