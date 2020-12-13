package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.data.repositories.local.DatabaseConstants

/**
 * UserModel class is used for receiving user related data from api end point
 * It also acts as table for room database
 */
@Entity(tableName = DatabaseConstants.DATABASE_TABLE_USER)
data class UserModel(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String
)