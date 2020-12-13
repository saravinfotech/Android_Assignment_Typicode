package com.assignment.data.repositories.local

import androidx.lifecycle.LiveData
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import javax.inject.Inject

/**
 * Implementation for the LocalRepo Interface
 */
class LocalRepoImpl @Inject constructor(
    private val localRepoDao: LocalRepo
) : LocalRepo {

    /**
     * Add List of Album Models in local database
     */
    override suspend fun addAlbums(models: List<AlbumModel>) {
        localRepoDao.addAlbums(models)
    }

    /**
     * Get List of Album Models from local database
     */
    override fun getAlbums(): LiveData<List<AlbumModel>> {
        return localRepoDao.getAlbums()
    }

    /**
     * Add User Model in local database
     */
    override suspend fun addUser(model: UserModel) {
        localRepoDao.addUser(model)
    }

    /**
     * Get User Model from local database for given user id
     */
    override fun getUser(userId: Int): LiveData<UserModel?> {
        return localRepoDao.getUser(userId)
    }

    /**
     * Add Album Models in local database
     */
    override suspend fun addAlbumPhotos(models: List<AlbumPhotosModel>) {
        localRepoDao.addAlbumPhotos(models)
    }

    /**
     * Get Album Photos from local database for given album id
     */
    override fun getAlbumPhotos(albumId: Int): LiveData<List<AlbumPhotosModel>> {
        return localRepoDao.getAlbumPhotos(albumId)
    }

    /**
     * Add Post Model in local database
     */
    override suspend fun addPostModel(model: PostModel) {
        return localRepoDao.addPostModel(model)
    }

    /**
     * Add Post Models in local database
     */
    override suspend fun addPostModels(models: List<PostModel>) {
        localRepoDao.addPostModels(models)
    }

    /**
     * Get Post Model by id
     */
    override fun getPostById(id: Int): LiveData<PostModel?> {
        return localRepoDao.getPostById(id)
    }

    /**
     * Get List of Post Models from local database
     */
    override fun getPostsByUserId(userId: Int): LiveData<List<PostModel>> {
        return localRepoDao.getPostsByUserId(userId)
    }

}