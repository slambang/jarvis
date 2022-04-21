package com.jarvis.app.domain.interactors

import com.jarvis.app.data.JarvisAppSettings
import com.jarvis.app.data.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    val settingsFlow: Flow<JarvisAppSettings>
        get() = settingsRepository.toFlow

    var isJarvisActive: Boolean
        get() = settingsRepository.isJarvisActive
        set(value) {
            settingsRepository.isJarvisActive = value
        }

    var isJarvisLocked: Boolean
        get() = settingsRepository.isJarvisLocked
        set(value) {
            settingsRepository.isJarvisLocked = value
        }
}
