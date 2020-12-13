package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.data.repositories.local.DatabaseConstants

/**
 * AlbumModel class is used for receiving Album related data from api end point
 * It also acts as table for room database
 */
@Entity(tableName = DatabaseConstants.DATABASE_TABLE_ALBUM)
data class AlbumModel(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    var thumbnailUrl: String?
)