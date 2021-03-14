package com.jarvis.app.data.settings

import android.content.SharedPreferences
import com.jarvis.app.BuildConfig
import kotlinx.coroutines.flow.*

data class JarvisAppSettings(
    val isJarvisActive: Boolean,
    val isJarvisLocked: Boolean
)

class SettingsRepository(
    private val prefs: SharedPreferences
) {
    private val _state: MutableStateFlow<JarvisAppSettings> = MutableStateFlow(getLatestModel())
    val toFlow: Flow<JarvisAppSettings>
        get() = _state

    var isJarvisActive: Boolean
        get() = prefs.getBoolean(IS_JARVIS_ACTIVE, BuildConfig.IS_JARVIS_ACTIVE_DEFAULT)
        set(value) = updateSetting(IS_JARVIS_ACTIVE, value)

    var isJarvisLocked: Boolean
        get() = prefs.getBoolean(IS_JARVIS_LOCKED, BuildConfig.IS_JARVIS_LOCKED_DEFAULT)
        set(value) = updateSetting(IS_JARVIS_LOCKED, value)

    private fun updateSetting(name: String, value: Boolean) {
        prefs.edit().putBoolean(name, value).apply()
        _state.update { getLatestModel() }
    }

    private fun getLatestModel(): JarvisAppSettings =
        JarvisAppSettings(
            isJarvisActive,
            isJarvisLocked
        )

    companion object {
        private const val IS_JARVIS_ACTIVE = "IS_JARVIS_ACTIVE"
        private const val IS_JARVIS_LOCKED = "IS_JARVIS_LOCKED"
    }
}
