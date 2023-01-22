package com.jarvis.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jarvis.app.data.database.entity.JarvisGroupEntity

@Dao
interface JarvisGroupDao {

    @Query("SELECT * FROM JarvisGroupEntity")
    fun getAllGroups(): List<JarvisGroupEntity>

    @Insert
    fun insertGroup(jarvisGroupEntity: JarvisGroupEntity)

    @Query("DELETE from JarvisGroupEntity")
    fun deleteAllGroups()
}
