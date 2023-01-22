package com.jarvis.app.domain.interactors

import com.jarvis.app.data.JarvisAppSettings
import com.jarvis.app.data.SettingsRepository
import com.jarvis.app.di.BackgroundCoroutine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @BackgroundCoroutine private val coroutineDispatcher: CoroutineDispatcher
) {
    val settingsFlow: Flow<JarvisAppSettings>
        get() = settingsRepository.toFlow

    suspend fun getIsJarvisActive(): Boolean =
        withContext(coroutineDispatcher) {
            settingsRepository.isJarvisActive
        }

    suspend fun setIsJarvisActive(isActive: Boolean): Unit =
        withContext(coroutineDispatcher) {
            settingsRepository.isJarvisActive = isActive
        }

    suspend fun getIsJarvisLocked(): Boolean =
        withContext(coroutineDispatcher) {
            settingsRepository.isJarvisLocked
        }

    suspend fun setIsJarvisLocked(isLocked: Boolean): Unit =
        withContext(coroutineDispatcher) {
            settingsRepository.isJarvisLocked = isLocked
        }
}
