package com.assignment.data.repositories.remote

import com.assignment.data.api.ApiService
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation for the RemoteRepo Interface
 */
class RemoteRepoImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteRepo {

    override suspend fun getAlbums(): Response<List<AlbumModel>> {
        return apiService.getAlbums()
    }

    override suspend fun getUser(userId: Int): Response<UserModel> {
        return apiService.getUser(userId)
    }

    override suspend fun getAlbumPhotos(albumId: Int): Response<List<AlbumPhotosModel>> {
        return apiService.getAlbumPhotos(albumId)
    }

    override suspend fun getAlbumPhotos(): Response<List<AlbumPhotosModel>> {
        return apiService.getAlbumPhotos()
    }

    override suspend fun getPostById(id: Int): Response<PostModel> {
        return apiService.getPostById(id)
    }

    override suspend fun getPostsByUserId(userId: Int): Response<List<PostModel>> {
        return apiService.getPostsByUserId(userId)
    }

}