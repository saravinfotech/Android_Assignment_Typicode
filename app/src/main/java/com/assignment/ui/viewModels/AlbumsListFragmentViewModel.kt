package com.assignment.ui.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.common.GenericResponse
import com.assignment.common.Logger
import com.assignment.data.models.AlbumModel
import com.assignment.data.repositories.local.LocalRepo
import com.assignment.data.repositories.remote.RemoteRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsListFragmentViewModel @ViewModelInject constructor(
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
     * Get List of Album Models from local database
     */
    fun getAlbumsFromLocalRepo() = localRepo.getAlbums()

    /**
     * getAlbums()
     *
     * Get the list of albums
     */
    fun getAlbumsFromRemoteRepo() = viewModelScope.launch {
        apiResponse.value = GenericResponse.loading()
        try {
            remoteRepo.getAlbums().let { response ->
                // if response is successful, save data in local repo
                if (response.isSuccessful) {
                    apiResponse.value = GenericResponse.success(response.body())
                    response.body()?.let {
                        localRepo.addAlbums(it)
                        getAlbumPhotosFromRemoteRepo(it)
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
     * getAlbumPhotos()
     *
     * Get the list of albums
     */
    private fun getAlbumPhotosFromRemoteRepo(albumsModel: List<AlbumModel>) = CoroutineScope(Dispatchers.IO).launch {
        try {
            remoteRepo.getAlbumPhotos().let { response ->
                // if response is successful, save data in local repo
                if (response.isSuccessful) {
                    response.body()?.let {
                        localRepo.addAlbumPhotos(it)
                        albumsModel.forEach { album ->
                            it.forEach { albumPhotosModel ->
                                if (albumPhotosModel.albumId == album.id) {
                                    album.thumbnailUrl = albumPhotosModel.thumbnailUrl
                                }
                            }
                        }
                        Logger.info("$albumsModel")
                        localRepo.addAlbums(albumsModel)
                    }
                }
            }
        } catch (e: Exception) {
            Logger.info("$e")
        }
    }

}