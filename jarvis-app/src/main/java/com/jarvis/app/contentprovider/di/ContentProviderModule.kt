package com.jarvis.app.contentprovider.di

import com.jarvis.app.domain.fields.JarvisContentProviderController
import org.koin.dsl.module

val contentProviderModule = module {

    single {
        JarvisContentProviderController(get(), get())
    }
}
