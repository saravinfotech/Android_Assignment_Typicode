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

    fun apiResponse() = apiResponse

    fun getUserFromLocalRepo(userId: Int) = localRepo.getUser(userId)

    fun getAlbumPhotosFromLocalRepo(albumId: Int) = localRepo.getAlbumPhotos(albumId)

    fun getPostByIdFromLocalDatabase(id: Int) = localRepo.getPostById(id)

    fun getPostsByUserIdFromLocalDatabase(userId: Int) = localRepo.getPostsByUserId(userId)

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