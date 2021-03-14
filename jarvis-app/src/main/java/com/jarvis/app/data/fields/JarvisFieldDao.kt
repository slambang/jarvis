package com.jarvis.app.data.fields

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JarvisFieldDao {

    @Query("SELECT * FROM JarvisFieldEntity")
    fun getAllFields(): Flow<List<JarvisFieldEntity>>

    @Query("SELECT * from JarvisFieldEntity WHERE name is :name")
    fun getField(name: String): JarvisFieldEntity?

    @Insert
    fun insertField(jarvisFieldEntity: JarvisFieldEntity)

    @Update
    fun updateField(jarvisFieldEntity: JarvisFieldEntity)

    @Query("DELETE from JarvisFieldEntity")
    fun deleteAllFields()
}
