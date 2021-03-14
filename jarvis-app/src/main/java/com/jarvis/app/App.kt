package com.jarvis.app

import android.app.Application
import android.content.Context
import com.jarvis.app.contentprovider.di.contentProviderModule
import com.jarvis.app.domain.di.domainModule
import com.jarvis.app.data.di.dataModule
import com.jarvis.app.view.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("UNUSED")
class App : Application() {

    /**
     * [com.jarvis.app.contentprovider.JarvisContentProvider]
     * is initialised before Application::onCreate.
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(dataModule, domainModule, viewModule, contentProviderModule)
        }
    }
}
