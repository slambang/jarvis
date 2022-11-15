package com.jarvis.demo.advanced.repository

import org.koin.dsl.module

/**
 * Only seen by the `release` build variant.
 * This app uses Koin for its simplicity. Replace with your preferred DI framework.
 */
val configRepositoryModule = module {

    /**
     * Inject the single interface to the rest of the app.
     * See [com.jarvis.demo.advanced.MainActivity.configRepository].
     */
    single<ConfigRepository> {
        ReleaseConfigRepository(get())
    }
}
