package com.jarvis.app.contentprovider

import android.content.Context
import com.jarvis.app.domain.fields.JarvisContentProviderController
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

    fun jarvisContentProviderController(): JarvisContentProviderController

    companion object {
        fun getController(context: Context): JarvisContentProviderController =
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                JarvisContentProviderEntryPoint::class.java
            ).jarvisContentProviderController()
    }
}
