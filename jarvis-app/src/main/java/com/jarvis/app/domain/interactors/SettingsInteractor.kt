package com.jarvis.app.domain.interactors

import com.jarvis.app.data.JarvisAppSettings
import com.jarvis.app.data.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    val settingsFlow: Flow<JarvisAppSettings>
        get() = settingsRepository.toFlow

    suspend fun getIsJarvisActive(): Boolean =
        withContext(Dispatchers.IO) {
            settingsRepository.isJarvisActive
        }

    suspend fun setIsJarvisActive(isActive: Boolean): Unit =
        withContext(Dispatchers.IO) {
            settingsRepository.isJarvisActive = isActive
        }

    suspend fun getIsJarvisLocked(): Boolean =
        withContext(Dispatchers.IO) {
            settingsRepository.isJarvisLocked
        }

    suspend fun setIsJarvisLocked(isLocked: Boolean): Unit =
        withContext(Dispatchers.IO) {
            settingsRepository.isJarvisLocked = isLocked
        }
}
