package com.jarvis.app.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.jarvis.app.data.database.entity.JarvisSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JarvisSettingsDao {

    @Query("SELECT * FROM JarvisSettingsEntity WHERE _id is -1")
    fun getSettings(): Flow<List<JarvisSettingsEntity>>

    @Query("SELECT isJarvisActive FROM JarvisSettingsEntity WHERE _id is -1")
    fun getIsJarvisActive(): Boolean

    @Query("UPDATE JarvisSettingsEntity SET isJarvisActive = :isActive WHERE _id is -1")
    fun setIsJarvisActive(isActive: Boolean)

    @Query("SELECT isJarvisLocked FROM JarvisSettingsEntity WHERE _id is -1")
    fun getIsJarvisLocked(): Boolean

    @Query("UPDATE JarvisSettingsEntity SET isJarvisLocked = :isLocked WHERE _id is -1")
    fun setIsJarvisLocked(isLocked: Boolean)
}
