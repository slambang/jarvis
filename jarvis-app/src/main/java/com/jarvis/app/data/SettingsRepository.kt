package com.jarvis.app.data

import android.content.Context
import com.jarvis.app.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class JarvisAppSettings(
    val isJarvisActive: Boolean,
    val isJarvisLocked: Boolean
)

class SettingsRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("jarvis_app_settings", Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(getLatestModel())
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
