package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.data.repositories.local.DatabaseConstants

/**
 * PostModel class is used for receiving post related data from api end point
 * It also acts as table for room database
 */
@Entity(tableName = DatabaseConstants.DATABASE_TABLE_POSTS)
data class PostModel(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)