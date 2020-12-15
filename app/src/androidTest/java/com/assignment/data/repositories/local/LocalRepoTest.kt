package com.assignment.data.repositories.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel
import com.assignment.util.GsonUtil
import com.assignment.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocalRepoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: LocalDatabase
    lateinit var localRepo: LocalRepo

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        localRepo = database.getDao()
    }

    @Test
    fun testAddAlbums() = runBlockingTest {
        val albumFromJSON = GsonUtil.fromJson<List<AlbumModel>>("albums_response.json")
        localRepo.addAlbums(albumFromJSON)

        val albumFromDB = localRepo.getAlbums().getOrAwaitValue()
        assertEquals(albumFromJSON.sortedBy { it.id }, albumFromDB.sortedBy { it.id })
        assertEquals(albumFromJSON.size, albumFromDB.size)
    }

    @Test
    fun testAddUser() = runBlockingTest {
        val userDetailFromJSON = GsonUtil.fromJson<UserModel>("user_response_4.json")
        localRepo.addUser(userDetailFromJSON)

        val userDetailFromDB = localRepo.getUser(userDetailFromJSON.id).getOrAwaitValue()
        assertEquals(userDetailFromJSON, userDetailFromDB)
    }


    @Test
    fun testAddAlbumPhotos() = runBlockingTest {
        val albumPhotosFromJSON = GsonUtil.fromJson<List<AlbumPhotosModel>>("album_photos.json")
        localRepo.addAlbumPhotos(albumPhotosFromJSON)

        val albumPhotoFromDB =
            localRepo.getAlbumPhotos(albumPhotosFromJSON[0].albumId).getOrAwaitValue()
        assertEquals(albumPhotosFromJSON, albumPhotoFromDB)
    }

    @Test
    fun testAddPostModel() = runBlockingTest {
        val albumPostsFromJson = GsonUtil.fromJson<List<PostModel>>("album_posts.json")
        localRepo.addPostModel(albumPostsFromJson[0])

        val albumPostFromDB = localRepo.getPostById(albumPostsFromJson[0].id).getOrAwaitValue()
        assertEquals(albumPostsFromJson[0], albumPostFromDB)
    }

    @Test
    fun testAddPostModels() = runBlockingTest {
        val albumPostsFromJson = GsonUtil.fromJson<List<PostModel>>("album_posts.json")
        localRepo.addPostModels(albumPostsFromJson)

        val albumPostFromDB =
            localRepo.getPostsByUserId(albumPostsFromJson[0].userId).getOrAwaitValue { }
        for (albumPosts in albumPostFromDB) {
            assertThat(albumPostsFromJson).contains(albumPosts)
        }
    }

    @After
    fun teardown() {
        database.close()
    }
}