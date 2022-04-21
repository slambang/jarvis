package com.jarvis.demo.advanced

import android.app.Application
import com.jarvis.demo.advanced.repository.FirebaseRemoteConfig
import com.jarvis.demo.advanced.repository.configRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("UNUSED")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                configRepositoryModule,
                module { single { FirebaseRemoteConfig() } }
            )
        }
    }
}
