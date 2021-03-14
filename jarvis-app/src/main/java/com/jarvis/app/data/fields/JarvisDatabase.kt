package com.jarvis.app.data.fields

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [JarvisFieldEntity::class]
)
internal abstract class JarvisDatabase : RoomDatabase() {

    abstract val jarvisFieldDao: JarvisFieldDao

    companion object {

        fun newInstance(context: Context): JarvisDatabase =
            Room.databaseBuilder(
                context,
                JarvisDatabase::class.java,
                "jarvis_database"
            )
            .build()
    }
}
