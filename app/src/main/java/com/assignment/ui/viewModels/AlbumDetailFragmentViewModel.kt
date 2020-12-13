package com.assignment.ui.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.common.GenericResponse
import com.assignment.data.repositories.local.LocalRepo
import com.assignment.data.repositories.remote.RemoteRepo
import kotlinx.coroutines.launch

class AlbumDetailFragmentViewModel @ViewModelInject constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo
) : ViewModel() {

    private val apiResponse: MutableLiveData<GenericResponse> = MutableLiveData()

    /**
     * This method let the activity knows the status of API call
     * status can be
     * Loading
     * Error
     * Success
     */
    fun apiResponse() = apiResponse

    /**
     * Get User Model from local database for given user id
     */
    fun getUserFromLocalRepo(userId: Int) = localRepo.getUser(userId)

    /**
     * Get Album Photos from local database for given album id
     */
    fun getAlbumPhotosFromLocalRepo(albumId: Int) = localRepo.getAlbumPhotos(albumId)

    /**
     * Get Post Model by id
     */
    fun getPostByIdFromLocalDatabase(id: Int) = localRepo.getPostById(id)

    /**
     * Get List of Post Models from local database
     */
    fun getPostsByUserIdFromLocalDatabase(userId: Int) = localRepo.getPostsByUserId(userId)

    /**
     * getAlbumPhotos(albumId: Int)
     *
     * Get album's photos
     */
    fun getAlbumPhotosFromRemoteRepo(albumId: Int) = viewModelScope.launch {
        // set api response to loading
        apiResponse.value = GenericResponse.loading()
        try {
            remoteRepo.getAlbumPhotos(albumId).let { response ->
                // if response is successful, save data in local repo
                if (response.isSuccessful) {
                    apiResponse.value = GenericResponse.success(response.body())
                    response.body()?.let {
                        localRepo.addAlbumPhotos(it)
                    }
                } else {
                    // some thing went wrong - show error to user
                    val errorMessage = response.errorBody().toString()
                    throw Exception(errorMessage)
                }
            }
        } catch (e: Exception) {
            // some thing went wrong - show error to user
            apiResponse.value =
                GenericResponse.error(e)
        }
    }

    /**
     * getUser(userId: Int)
     *
     * Get the details about user
     */
    fun getUserFromRemoteRepo(userId: Int) = viewModelScope.launch {
        apiResponse.value = GenericResponse.loading()
        try {
            remoteRepo.getUser(userId).let { response ->
                // if response is successful, save data in local repo
                if (response.isSuccessful) {
                    apiResponse.value = GenericResponse.success(response.body())
                    response.body()?.let {
                        localRepo.addUser(it)
                    }
                } else {
                    // some thing went wrong - show error to user
                    val errorMessage = response.errorBody().toString()
                    throw Exception(errorMessage)
                }
            }
        } catch (e: Exception) {
            // some thing went wrong - show error to user
            apiResponse.value =
                GenericResponse.error(e)
        }
    }

    /**
     * getPostById(id: Int)
     *
     * Get Post by id
     */
    fun getPostByIdFromRemoteRepo(id: Int) = viewModelScope.launch {
        apiResponse.value = GenericResponse.loading()
        try {
            remoteRepo.getPostById(id).let { response ->
                // if response is successful, save data in local repo
                if (response.isSuccessful) {
                    apiResponse.value = GenericResponse.success(response.body())
                    response.body()?.let {
                        localRepo.addPostModel(it)
                    }
                } else {
                    // some thing went wrong - show error to user
                    val errorMessage = response.errorBody().toString()
                    throw Exception(errorMessage)
                }
            }
        } catch (e: Exception) {
            // some thing went wrong - show error to user
            apiResponse.value =
                GenericResponse.error(e)
        }
    }

    /**
     * getPostsByUserId(userId: Int)
     *
     * Get List of Posts by user id
     */
    fun getPostsByUserIdFromRemoteRepo(userId: Int) = viewModelScope.launch {
        apiResponse.value = GenericResponse.loading()
        try {
            remoteRepo.getPostsByUserId(userId).let { response ->
                // if response is successful, save data in local repo
                if (response.isSuccessful) {
                    apiResponse.value = GenericResponse.success(response.body())
                    response.body()?.let {
                        localRepo.addPostModels(it)
                    }
                } else {
                    // some thing went wrong - show error to user
                    val errorMessage = response.errorBody().toString()
                    throw Exception(errorMessage)
                }
            }
        } catch (e: Exception) {
            // some thing went wrong - show error to user
            apiResponse.value =
                GenericResponse.error(e)
        }
    }

}