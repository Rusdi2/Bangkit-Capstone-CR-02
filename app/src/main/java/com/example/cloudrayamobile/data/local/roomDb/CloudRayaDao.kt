package com.example.cloudrayamobile.data.local.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CloudRayaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSite(siteListEntity: SiteListEntity)

    @Delete
    fun deleteSite(siteListEntity: SiteListEntity)

    @Query("SELECT * FROM siteListEntity ORDER BY siteId")
    fun getAllSiteList(): LiveData<List<SiteListEntity>>
}