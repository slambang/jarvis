package com.jarvis.app.domain.fields

import com.jarvis.app.domain.interactors.SettingsInteractor
import com.jarvis.app.domain.usecases.GetSingleFieldUseCase
import com.jarvis.app.domain.usecases.RefreshConfigUseCase
import com.jarvis.client.data.StringListField
import java.io.InputStream
import javax.inject.Inject

class JarvisContentProviderViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val getSingleField: GetSingleFieldUseCase,
    private val refreshConfig: RefreshConfigUseCase
) {
    val isJarvisLocked: Boolean
        get() = settingsInteractor.isJarvisLocked

    val isJarvisActive: Boolean
        get() = settingsInteractor.isJarvisActive

    fun onConfigPush(inputStream: InputStream) {
        refreshConfig(inputStream).also {
            settingsInteractor.isJarvisLocked = it.lockAfterPush
        }
    }

    fun getFieldValue(fieldName: String): String? {
        val field = getSingleField(fieldName)
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
