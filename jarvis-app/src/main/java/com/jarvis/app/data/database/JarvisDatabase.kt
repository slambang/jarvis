package com.jarvis.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jarvis.app.data.database.dao.JarvisFieldDao
import com.jarvis.app.data.database.dao.JarvisSettingsDao
import com.jarvis.app.data.database.entity.JarvisFieldEntity
import com.jarvis.app.data.database.entity.JarvisSettingsEntity

@Database(
    version = 1,
    entities = [
        JarvisFieldEntity::class,
        JarvisSettingsEntity::class
    ]
)
abstract class JarvisDatabase : RoomDatabase() {

    abstract val jarvisFieldDao: JarvisFieldDao

    abstract val jarvisSettingsDao: JarvisSettingsDao

    companion object {

        fun newInstance(context: Context): JarvisDatabase =
            Room.databaseBuilder(
                context,
                JarvisDatabase::class.java,
                "jarvis_database"
            )
            .addCallback(insertDefaultJarvisSettings)
            .build()

        private val insertDefaultJarvisSettings: Callback = object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO JarvisSettingsEntity (_id,isJarvisActive,isJarvisLocked) VALUES(-1,1,0);")
                }
            }
    }
}
