package com.assignment.data.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel

/**
 * LocalDatabase - Abstract class for extending the Room database
 * We have also mentioned the database tables ( model classes, i-e AlbumModel,AlbumPhotosModel & UserModel ) and database version
 */
@Database(
    entities = [AlbumModel::class, AlbumPhotosModel::class, UserModel::class, PostModel::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getDao(): LocalRepo
}