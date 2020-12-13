package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.data.repositories.local.DatabaseConstants

/**
 * AlbumPhotosModel class is used for receiving photos related data from api end point
 * It also acts as table for room database
 */
@Entity(tableName = DatabaseConstants.DATABASE_TABLE_ALBUM_PHOTOS)
data class AlbumPhotosModel(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val url: String,
    val thumbnailUrl: String
)