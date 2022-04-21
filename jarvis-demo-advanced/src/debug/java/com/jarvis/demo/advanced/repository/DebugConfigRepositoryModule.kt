package com.jarvis.demo.advanced.repository

import com.jarvis.client.JarvisClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Only seen by the `debug` build variant.
 * This app uses Koin for its simplicity. Replace with your preferred DI framework.
 */
val configRepositoryModule = module {

    single {
        JarvisClient.newInstance(androidContext())
    }

    /**
     * Inject the single interface to the rest of the app.
     * See [com.jarvis.demo.advanced.MainActivity.configRepository].
     */
    single<ConfigRepository> {
        val jarvisClient: JarvisClient = get()
        val firebaseRemoteConfig: FirebaseRemoteConfig = get()
        DebugConfigRepository(jarvisClient, firebaseRemoteConfig)
    }
}
