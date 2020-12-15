package com.assignment.ui.viewModels

import com.assignment.common.TestConstants.ALBUM_PHOTOS_MODEL
import com.assignment.common.TestConstants.POST_MODEL
import com.assignment.common.TestConstants.USER_MODEL
import com.assignment.common.getOrAwaitValue
import com.assignment.data.repositories.remote.FakeLocalRepoImpl
import com.assignment.data.repositories.remote.FakeRemoteRepoImpl
import com.assignment.ui.BaseTest
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumDetailFragmentViewModelTest : BaseTest() {

    private lateinit var viewModel: AlbumDetailFragmentViewModel

    @Before
    fun setup() {
        viewModel = AlbumDetailFragmentViewModel(FakeRemoteRepoImpl(), FakeLocalRepoImpl())
    }

    @Test
    fun testGetAlbumPhotosFromRemoteRepo() {
        viewModel.getAlbumPhotosFromRemoteRepo(1)

        val response = viewModel.getAlbumPhotosFromLocalRepo(1).getOrAwaitValue()

        for (albumPhotos in response) {
            assertThat(ALBUM_PHOTOS_MODEL).contains(albumPhotos)
        }
    }


    @Test
    fun testGetUserFromRemoteRepo() {
        viewModel.getUserFromRemoteRepo(4)

        val response = viewModel.getUserFromLocalRepo(4).getOrAwaitValue()
        assertEquals(USER_MODEL, response)
    }

    @Test
    fun testGetPostByIdFromRemoteRepo() {
        viewModel.getPostByIdFromRemoteRepo(2)

        val response = viewModel.getPostByIdFromLocalDatabase(2).getOrAwaitValue()
        assertThat(POST_MODEL).contains(response)
    }

    @Test
    fun testGetPostsByUserIdFromRemoteRepo() {
        viewModel.getPostsByUserIdFromRemoteRepo(2)

        val response = viewModel.getPostsByUserIdFromLocalDatabase(2).getOrAwaitValue()
        for (posts in response) {
            assertThat(POST_MODEL).contains(posts)
        }
    }
}