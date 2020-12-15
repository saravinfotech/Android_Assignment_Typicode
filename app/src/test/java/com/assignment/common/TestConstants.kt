package com.assignment.common

import com.assignment.data.models.AlbumModel
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.data.models.PostModel
import com.assignment.data.models.UserModel

object TestConstants {

    val ALBUM_MODEL = TestUtils.fromJson<List<AlbumModel>>("albums_response.json")
    val USER_MODEL = TestUtils.fromJson<UserModel>("user_response_4.json")
    val ALBUM_PHOTOS_MODEL = TestUtils.fromJson<List<AlbumPhotosModel>>("album_photos.json")
    val POST_MODEL = TestUtils.fromJson<List<PostModel>>("album_posts.json")
}