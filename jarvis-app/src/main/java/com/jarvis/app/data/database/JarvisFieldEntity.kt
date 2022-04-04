package com.jarvis.app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JarvisFieldEntity(
    @PrimaryKey
    @ColumnInfo
    val name: String,

    @ColumnInfo
    val jsonModel: String
)
