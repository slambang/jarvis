package com.jarvis.app.data.di

import android.content.Context
import com.jarvis.app.domain.fields.JarvisFieldRepository
import com.jarvis.app.data.settings.SettingsRepository
import com.jarvis.app.data.fields.JarvisDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        JarvisDatabase.newInstance(androidContext()).jarvisFieldDao
    }

    single {
        JarvisFieldRepository(get())
    }

    single {
        androidContext().getSharedPreferences(
            "jarvis_settings",
            Context.MODE_PRIVATE
        )
    }

    single {
        SettingsRepository(get())
    }
}
