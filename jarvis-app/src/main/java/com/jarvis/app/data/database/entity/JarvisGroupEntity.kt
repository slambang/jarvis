package com.jarvis.app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JarvisGroupEntity(
    @PrimaryKey
    @ColumnInfo
    val name: String,

    @ColumnInfo
    val isCollapsable: Boolean,

    @ColumnInfo
    val startCollapsed: Boolean
)
