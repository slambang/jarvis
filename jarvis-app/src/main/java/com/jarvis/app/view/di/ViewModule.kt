package com.jarvis.app.view.di

import com.jarvis.app.view.util.ResourceProvider
import com.jarvis.app.view.main.MainActivityViewModel
import com.jarvis.app.view.main.MainModelMapper
import com.jarvis.app.view.main.ValidatorMapper
import com.jarvis.app.view.main.editfielddialog.EditFieldDialogFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    viewModel {
        MainActivityViewModel(get(), get(), get())
    }

    factory {
        ResourceProvider(androidContext())
    }

    factory {
        EditFieldDialogFactory()
    }

    factory {
        ValidatorMapper(get())
    }

    factory {
        MainModelMapper(get(), get())
    }
}
