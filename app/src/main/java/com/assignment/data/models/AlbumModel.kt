package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.common.Constants

@Entity(tableName = Constants.ALBUM_TABLE)
data class AlbumModel(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    var thumbnailUrl: String?
)