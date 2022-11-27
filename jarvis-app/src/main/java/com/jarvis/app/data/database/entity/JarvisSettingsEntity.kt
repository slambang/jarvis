package com.jarvis.app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JarvisSettingsEntity(
    @PrimaryKey
    val _id: Int = -1,

    @ColumnInfo
    val isJarvisActive: Boolean,

    @ColumnInfo
    val isJarvisLocked: Boolean
)
