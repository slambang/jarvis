package com.jarvis.app.di

import android.content.Context
import com.jarvis.app.data.database.JarvisDatabase
import com.jarvis.app.data.database.dao.JarvisFieldDao
import com.jarvis.app.data.database.dao.JarvisSettingsDao
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
    @Singleton
    fun provideJarvisFieldDao(database: JarvisDatabase): JarvisFieldDao =
        database.jarvisFieldDao

    @Provides
    @Singleton
    fun provideJarvisSettingsDao(database: JarvisDatabase): JarvisSettingsDao =
        database.jarvisSettingsDao
}
