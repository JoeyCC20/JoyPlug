package com.joybox.joyplug.host.core.loader.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PluginEntity::class], version = 1, exportSchema = false)
abstract class InstalledPluginsDataBase : RoomDatabase() {

    abstract fun pluginDao() : PluginDao

    companion object {
        @Volatile
        private var INSTANCE : InstalledPluginsDataBase? = null
        fun getDatabase(context : Context) : InstalledPluginsDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                                              InstalledPluginsDataBase::class.java,
                                        "installed_plugins")
                                .fallbackToDestructiveMigration()
                                .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}