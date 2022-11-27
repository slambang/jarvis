package com.jarvis.demo.advanced

/**
 * This interface is injected into [MainActivity].
 *
 * The implementation will depend on the build variant:
 *  `debug` (with Jarvis): [com.jarvis.demo.advanced.repository.DebugConfigRepository]
 *  `release` (no Jarvis): [com.jarvis.demo.advanced.repository.ReleaseConfigRepository]
 */
interface ConfigRepository {
    fun getStringValue(): String
}
