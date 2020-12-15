package com.assignment.ui.viewModels

import com.assignment.common.TestConstants.ALBUM_MODEL
import com.assignment.common.getOrAwaitValue
import com.assignment.data.repositories.remote.FakeLocalRepoImpl
import com.assignment.data.repositories.remote.FakeRemoteRepoImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsListFragmentViewModelTest : BaseTest() {
    private lateinit var viewModel: AlbumsListFragmentViewModel

    @Before
    fun setup() {
        viewModel = AlbumsListFragmentViewModel(FakeRemoteRepoImpl(), FakeLocalRepoImpl())
    }

    @Test
    fun test() {
        viewModel.getAlbumsFromRemoteRepo()

        val response = viewModel.getAlbumsFromLocalRepo().getOrAwaitValue()

        assertEquals(ALBUM_MODEL, response)
    }
}