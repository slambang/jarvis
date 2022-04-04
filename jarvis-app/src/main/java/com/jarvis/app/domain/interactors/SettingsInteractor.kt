package com.jarvis.app.domain.interactors

import com.jarvis.app.data.JarvisAppSettings
import com.jarvis.app.data.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    // Called by ViewModel
    val settingsFlow: Flow<JarvisAppSettings>
        get() = settingsRepository.toFlow

    // Called by ViewModel and ContentProvider
    var isJarvisActive: Boolean
        get() = settingsRepository.isJarvisActive
        set(value) {
            settingsRepository.isJarvisActive = value
        }

    // Called by ViewModel and ContentProvider
    var isJarvisLocked: Boolean
        get() = settingsRepository.isJarvisLocked
        set(value) {
            settingsRepository.isJarvisLocked = value
        }
}
