package com.jarvis.demo.advanced

/**
 * This interface is injected into [MainActivity].
 *
 * The implementation will depend on the build variant:
 *  debug (with Jarvis): DebugConfigRepository.kt
 *  release (no Jarvis): ReleaseConfigRepository.kt
 */
interface ConfigRepository {
    fun getStringValue(): String
}
