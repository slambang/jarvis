package com.jarvis.app.di

import android.content.Context
import com.jarvis.app.domain.fields.JarvisContentProviderViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * This is a work around for Hilt not supporting injection into ContentProviders.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface JarvisContentProviderEntryPoint {

    fun jarvisContentProviderController(): JarvisContentProviderViewModel

    companion object {
        fun getViewModel(context: Context): JarvisContentProviderViewModel =
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                JarvisContentProviderEntryPoint::class.java
            ).jarvisContentProviderController()
    }
}
