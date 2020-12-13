package com.assignment.data.api

import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * ApiService defines the URL the app will hit to get data
 * It also defines the return type of each end point and any parameter if endpoint takes
 * It is used with Retrofit
 */
interface ApiService {

    /**
     * getAlbums()
     *
     * Get the list of albums
     */
    @GET("/albums")
    suspend fun getAlbums(): Response<List<AlbumModel>>

    /**
     * getUser(userId: Int)
     *
     * Get the details about user
     */
    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<UserModel>

    /**
     * getAlbumPhotos(albumId: Int)
     *
     * Get album's photos
     */
    @GET("/albums/{albumId}/photos")
    suspend fun getAlbumPhotos(@Path("albumId") albumId: Int): Response<List<AlbumPhotosModel>>

    /**
     * getAlbumPhotos()
     *
     * Get All Photos
     */
    @GET("/photos")
    suspend fun getAlbumPhotos(): Response<List<AlbumPhotosModel>>

    /**
     * getPostById(id: Int)
     *
     * Get Post by id
     */
    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<PostModel>

    /**
     * getPostsByUserId(userId: Int)
     *
     * Get List of Posts by user id
     */
    @GET("/posts")
    suspend fun getPostsByUserId(@Query("userId") userId: Int): Response<List<PostModel>>

}