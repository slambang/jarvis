package com.jarvis.client

/**
 * Result of [JarvisClient.pushConfigToJarvisApp].
 */
enum class JarvisPushConfigResult {
    /**
     * The config push succeeded.
     */
    SUCCESS,

    /**
     * The config push failed.
     */
    FAILURE,

    /**
     * The Jarvis app is locked and cannot accept new config.
     */
    LOCKED
}
