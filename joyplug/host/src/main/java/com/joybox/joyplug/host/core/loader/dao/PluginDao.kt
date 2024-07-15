package com.joybox.joyplug.host.core.loader.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PluginDao {
    @Insert
    fun insert(pluginEntity: PluginEntity)
    @Update
    fun update(pluginEntity: PluginEntity)
    @Delete
    fun delete(pluginEntity: PluginEntity)
    @Query("SELECT * FROM plugins")
    fun findAll() : List<PluginEntity>
    @Query("SELECT * FROM plugins WHERE mPackageName = :packageName")
    fun findById(packageName : String) : PluginEntity?
}