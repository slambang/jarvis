package com.jarvis.app.data

import android.content.Context
import com.jarvis.app.data.database.JarvisDatabase
import com.jarvis.app.data.database.JarvisFieldDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideJarvisDatabase(@ApplicationContext context: Context): JarvisDatabase =
        JarvisDatabase.newInstance(context)

    @Provides
    fun provideJarvisFieldDao(database: JarvisDatabase): JarvisFieldDao =
        database.jarvisFieldDao
}
