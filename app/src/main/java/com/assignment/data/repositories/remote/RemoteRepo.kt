package com.assignment.data.repositories.remote

import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * RemoteRepo Contract - Used for API calls
 */
interface RemoteRepo {

    /**
     * Get the list of albums
     */
    suspend fun getAlbums(): Response<List<AlbumModel>>

    /**
     * Get the details about user
     */
    suspend fun getUser(userId: Int): Response<UserModel>

    /**
     * Get album's photos
     */
    suspend fun getAlbumPhotos(albumId: Int): Response<List<AlbumPhotosModel>>

    /**
     * Get All Photos
     */
    @GET("/photos")
    suspend fun getAlbumPhotos(): Response<List<AlbumPhotosModel>>

    /**
     * Get Post by id
     */
    suspend fun getPostById(id: Int): Response<PostModel>

    /**
     * Get List of Posts by user id
     */
    suspend fun getPostsByUserId(@Query("userId") userId: Int): Response<List<PostModel>>
}