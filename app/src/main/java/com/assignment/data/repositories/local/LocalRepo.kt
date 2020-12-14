package com.assignment.data.repositories.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.common.Constants
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel

/**
 * DAO for local database
 * LocalRepo contract
 */
@Dao
interface LocalRepo {

    /**
     * Add albums to local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbums(models: List<AlbumModel>)

    /**
     * Add User Model in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(model: UserModel)

    /**
     * Add Album Models in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbumPhotos(models: List<AlbumPhotosModel>)

    /**
     * Add Post API response to local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostModel(model: PostModel)

    /**
     * Add Post Models in local database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostModels(models: List<PostModel>)


    /**
     * Get List of Album Models from local database
     */
    @Query("Select * from ${Constants.ALBUM_TABLE} order by title ASC")
    fun getAlbums(): LiveData<List<AlbumModel>>

    /**
     * Get User Model from local database for given user id
     */
    @Query("Select * from ${Constants.USERS_TABLE} where id = :userId")
    fun getUser(userId: Int): LiveData<UserModel?>

    /**
     * Get Album Photos from local database for given album id
     */
    @Query("Select * from ${Constants.ALBUM_PHOTOS_TABLE} where albumId = :albumId")
    fun getAlbumPhotos(albumId: Int): LiveData<List<AlbumPhotosModel>>

    /**
     * Get Post Model by id
     */
    @Query("Select * from ${Constants.ALBUM_POSTS_TABLE} where id = :id")
    fun getPostById(id: Int): LiveData<PostModel?>

    /**
     * Get List of Post Models from local database
     */
    @Query("Select * from ${Constants.ALBUM_POSTS_TABLE} where userId = :userId")
    fun getPostsByUserId(userId: Int): LiveData<List<PostModel>>

}