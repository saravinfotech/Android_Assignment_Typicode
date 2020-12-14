package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.common.Constants

@Entity(tableName = Constants.ALBUM_POSTS_TABLE)
data class PostModel(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)