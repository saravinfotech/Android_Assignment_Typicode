package com.assignment.data.repositories.remote

import com.assignment.common.TestConstants.ALBUM_MODEL
import com.assignment.common.TestConstants.ALBUM_PHOTOS_MODEL
import com.assignment.common.TestConstants.POST_MODEL
import com.assignment.common.TestConstants.USER_MODEL
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import retrofit2.Response

class FakeRemoteRepoImpl : RemoteRepo {

    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getAlbums(): Response<List<AlbumModel>> {
        return Response.success(ALBUM_MODEL)
    }

    override suspend fun getUser(userId: Int): Response<UserModel> {
        return Response.success(USER_MODEL)
    }

    override suspend fun getAlbumPhotos(albumId: Int): Response<List<AlbumPhotosModel>> {
        return Response.success(ALBUM_PHOTOS_MODEL)
    }

    override suspend fun getAlbumPhotos(): Response<List<AlbumPhotosModel>> {
        return Response.success(ALBUM_PHOTOS_MODEL)
    }

    override suspend fun getPostById(id: Int): Response<PostModel> {
        return Response.success(POST_MODEL.get(0))
    }

    override suspend fun getPostsByUserId(userId: Int): Response<List<PostModel>> {
        return Response.success(POST_MODEL)
    }

}