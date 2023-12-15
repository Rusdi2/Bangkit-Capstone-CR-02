package com.example.cloudrayamobile.data.local.roomDb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class SiteListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "siteId")
    var siteId: Int = 0,

    @ColumnInfo(name = "siteName")
    var siteName: String? = null,

    @ColumnInfo(name = "appKey")
    var appKey: String? = null,

    @ColumnInfo(name = "secretKey")
    var secretKey: String? = null
) : Parcelable