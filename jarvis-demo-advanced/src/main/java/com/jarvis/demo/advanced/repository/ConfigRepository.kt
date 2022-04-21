package com.jarvis.demo.advanced.repository

/**
 * This single interface is exposed to the rest of the app.
 * The implementation will depend on the build variant:
 *  `debug` (with Jarvis): [com.jarvis.demo.repository.DebugConfigRepository]
 *  `release` (no Jarvis): [com.jarvis.demo.repository.ReleaseConfigRepository]
 */
interface ConfigRepository {

    fun someStringValue(): String
}
