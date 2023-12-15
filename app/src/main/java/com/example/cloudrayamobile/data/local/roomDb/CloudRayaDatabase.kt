package com.example.cloudrayamobile.data.local.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SiteListEntity::class], version = 1)
abstract class CloudRayaDatabase : RoomDatabase() {
    abstract fun siteListDao(): CloudRayaDao

    companion object {
        @Volatile
        private var instance: CloudRayaDatabase? = null
        fun getInstance(context: Context): CloudRayaDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    CloudRayaDatabase::class.java, "CloudRaya_db"
                ).build()
            }
    }
}