package com.assignment.data.repositories.remote

import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * RemoteRepo - Its a contract {that this Repository will provide these below functions}
 *
 * RemoteRepoImpl is used to fulfill this contract
 *
 *  1) Now the function can be re-used
 *  2) The UI class only calls the RemoteRepo function and is unaware how the function actually executes
 *  3) We can provide different implementations for the same function by changing RemoteRepoImpl or adding another Repository Implementation
 *  4) Main code is only in 1 place, so easy to read/update
 *
 */
interface RemoteRepo {

    /**
     * getAlbums()
     *
     * Get the list of albums
     */
    suspend fun getAlbums(): Response<List<AlbumModel>>

    /**
     * getUser(userId: Int)
     *
     * Get the details about user
     */
    suspend fun getUser(userId: Int): Response<UserModel>

    /**
     * getAlbumPhotos(albumId: Int)
     *
     * Get album's photos
     */
    suspend fun getAlbumPhotos(albumId: Int): Response<List<AlbumPhotosModel>>

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
    suspend fun getPostById(id: Int): Response<PostModel>

    /**
     * getPostsByUserId(userId: Int)
     *
     * Get List of Posts by user id
     */
    suspend fun getPostsByUserId(@Query("userId") userId: Int): Response<List<PostModel>>
}