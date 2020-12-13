package com.assignment.data.repositories.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel

/**
 * Data Access Object for local database
 *
 * LocalRepo - Its a contract {that this Repository will provide these below functions}
 *
 * LocalRepoImpl is used to fulfill this contract
 *
 *  1) Now the function can be re-used
 *  2) The UI class only calls the LocalRepo function and is unaware how the function actually executes
 *  3) We can provide different implementations for the same function by changing LocalRepoImpl or adding another Repository Implementation
 *  4) Main code is only in 1 place, so easy to read/update
 *
 */
@Dao
interface LocalRepo {

    /**
     * Add List of Album Models in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbums(models: List<AlbumModel>)

    /**
     * Get List of Album Models from local database
     */
    @Query("Select * from ${DatabaseConstants.DATABASE_TABLE_ALBUM} order by title ASC")
    fun getAlbums(): LiveData<List<AlbumModel>>

    /**
     * Add User Model in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(model: UserModel)

    /**
     * Get User Model from local database for given user id
     */
    @Query("Select * from ${DatabaseConstants.DATABASE_TABLE_USER} where id = :userId")
    fun getUser(userId: Int): LiveData<UserModel?>

    /**
     * Add Album Models in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbumPhotos(models: List<AlbumPhotosModel>)

    /**
     * Get Album Photos from local database for given album id
     */
    @Query("Select * from ${DatabaseConstants.DATABASE_TABLE_ALBUM_PHOTOS} where albumId = :albumId")
    fun getAlbumPhotos(albumId: Int): LiveData<List<AlbumPhotosModel>>

    /**
     * Add Post Model in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostModel(model: PostModel)

    /**
     * Add Post Models in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostModels(models: List<PostModel>)

    /**
     * Get Post Model by id
     */
    @Query("Select * from ${DatabaseConstants.DATABASE_TABLE_POSTS} where id = :id")
    fun getPostById(id: Int): LiveData<PostModel?>

    /**
     * Get List of Post Models from local database
     */
    @Query("Select * from ${DatabaseConstants.DATABASE_TABLE_POSTS} where userId = :userId")
    fun getPostsByUserId(userId: Int): LiveData<List<PostModel>>

}