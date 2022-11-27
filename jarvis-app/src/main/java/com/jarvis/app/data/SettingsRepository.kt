package com.jarvis.app.data

import com.jarvis.app.data.database.dao.JarvisSettingsDao
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class JarvisAppSettings(
    val isJarvisActive: Boolean,
    val isJarvisLocked: Boolean
)

class SettingsRepository @Inject constructor(
    private val settingsDao: JarvisSettingsDao
) {
    val toFlow: Flow<JarvisAppSettings>
        get() = settingsDao.getSettings().map {
            with (it.single()) {
                JarvisAppSettings(isJarvisActive, isJarvisLocked)
            }
        }

    var isJarvisActive: Boolean
        get() = settingsDao.getIsJarvisActive()
        set(value) = settingsDao.setIsJarvisActive(value)

    var isJarvisLocked: Boolean
        get() = settingsDao.getIsJarvisLocked()
        set(value) = settingsDao.setIsJarvisLocked(value)
}
