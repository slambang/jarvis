package com.jarvis.app.domain.di

import com.jarvis.app.domain.interactors.FieldsInteractor
import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.domain.interactors.SettingsInteractor
import org.koin.dsl.module

val domainModule = module {

    single {
        AppJsonMapper()
    }

    factory {
        FieldsInteractor(get(), get())
    }

    factory {
        SettingsInteractor(get())
    }
}
