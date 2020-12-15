package com.assignment.data.repositories.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignment.common.TestConstants.ALBUM_MODEL
import com.assignment.common.TestConstants.ALBUM_PHOTOS_MODEL
import com.assignment.common.TestConstants.POST_MODEL
import com.assignment.common.TestConstants.USER_MODEL
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import com.assignment.data.repositories.local.LocalRepo

class FakeLocalRepoImpl : LocalRepo {

    private val albumModelLiveData = MutableLiveData(ALBUM_MODEL)
    private val userModelLiveData = MutableLiveData(USER_MODEL)
    private val albumPhotosModel = MutableLiveData(ALBUM_PHOTOS_MODEL)
    private val postModel = MutableLiveData(POST_MODEL[0])

    override suspend fun addAlbums(models: List<AlbumModel>) {}

    override suspend fun addUser(model: UserModel) {}

    override suspend fun addAlbumPhotos(models: List<AlbumPhotosModel>) {}

    override suspend fun addPostModel(model: PostModel) {}

    override suspend fun addPostModels(models: List<PostModel>) {}

    override fun getAlbums(): LiveData<List<AlbumModel>> {
        return albumModelLiveData
    }

    override fun getUser(userId: Int): LiveData<UserModel?> {
        return userModelLiveData
    }

    override fun getAlbumPhotos(albumId: Int): LiveData<List<AlbumPhotosModel>> {
        return albumPhotosModel
    }

    override fun getPostById(id: Int): LiveData<PostModel?> {
        return postModel
    }

    override fun getPostsByUserId(userId: Int): LiveData<List<PostModel>> {
        return MutableLiveData(POST_MODEL)
    }

}