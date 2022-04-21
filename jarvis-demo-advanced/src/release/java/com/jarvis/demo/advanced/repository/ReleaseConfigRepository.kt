package com.jarvis.demo.advanced.repository

/**
 * Only seen by the `release` build variant.
 */
class ReleaseConfigRepository(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : ConfigRepository {

    /**
     *  Directly calls the "real" remote config service to get the value.
     *  Do whatever you need to here, just return a value.
     */
    override fun someStringValue(): String = firebaseRemoteConfig.someStringValue()
}
