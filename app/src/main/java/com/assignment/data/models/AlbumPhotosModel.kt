package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.common.Constants

@Entity(tableName = Constants.ALBUM_PHOTOS_TABLE)
data class AlbumPhotosModel(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val url: String,
    val thumbnailUrl: String
)