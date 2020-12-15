package com.assignment

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.assignment.data.api.ApiService
import com.assignment.data.repositories.remote.RemoteRepo
import com.assignment.data.repositories.remote.RemoteRepoImpl
import com.assignment.ui.activities.MainActivity
import com.google.gson.Gson
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseTestClass {

    @get:Rule
    var myActivityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    val server: MockWebServer = MockWebServer()
    val serverPort = 8000

    lateinit var apiService: ApiService
    lateinit var placeholderApi: RemoteRepo


    @Before
    fun init() {
        server.start(serverPort)

        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ApiService::class.java)
        placeholderApi = RemoteRepoImpl(apiService)
    }


    @After
    fun shutdown() {
        server.shutdown()
    }
}