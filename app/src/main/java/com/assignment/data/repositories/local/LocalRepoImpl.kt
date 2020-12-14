package com.assignment.data.repositories.local

import androidx.lifecycle.LiveData
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import javax.inject.Inject

class LocalRepoImpl @Inject constructor(
    private val localRepoDao: LocalRepo
) : LocalRepo {

    /**
     * Methods to add the responses to database
     */
    override suspend fun addAlbums(models: List<AlbumModel>) {
        localRepoDao.addAlbums(models)
    }

    override suspend fun addUser(model: UserModel) {
        localRepoDao.addUser(model)
    }

    override suspend fun addAlbumPhotos(models: List<AlbumPhotosModel>) {
        localRepoDao.addAlbumPhotos(models)
    }

    override suspend fun addPostModel(model: PostModel) {
        return localRepoDao.addPostModel(model)
    }

    override suspend fun addPostModels(models: List<PostModel>) {
        localRepoDao.addPostModels(models)
    }


    /**
     * Methods to retrieve responses form the database
     */
    override fun getAlbums(): LiveData<List<AlbumModel>> {
        return localRepoDao.getAlbums()
    }

    override fun getUser(userId: Int): LiveData<UserModel?> {
        return localRepoDao.getUser(userId)
    }

    override fun getAlbumPhotos(albumId: Int): LiveData<List<AlbumPhotosModel>> {
        return localRepoDao.getAlbumPhotos(albumId)
    }

    override fun getPostById(id: Int): LiveData<PostModel?> {
        return localRepoDao.getPostById(id)
    }

    override fun getPostsByUserId(userId: Int): LiveData<List<PostModel>> {
        return localRepoDao.getPostsByUserId(userId)
    }

}