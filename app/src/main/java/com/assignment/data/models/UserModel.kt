package com.assignment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.common.Constants

@Entity(tableName = Constants.USERS_TABLE)
data class UserModel(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String
)