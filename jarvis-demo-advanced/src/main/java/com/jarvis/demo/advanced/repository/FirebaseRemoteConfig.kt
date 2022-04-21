package com.jarvis.demo.advanced.repository

/**
 * Seen by both `debug` and `release` build variants.
 * Represents your preferred (real) remote configuration service.
 * Replace this with real Firebase Remote Config, LaunchDarkly, Leanplum, etc.
 */
class FirebaseRemoteConfig {

    /**
     * Mock the call to the "real" Firebase Remote Config to get the value.
     */
    fun someStringValue(): String = "Real value"
}
