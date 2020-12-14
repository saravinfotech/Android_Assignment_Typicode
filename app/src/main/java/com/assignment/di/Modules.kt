package com.assignment.di

import android.content.Context
import androidx.room.Room
import com.assignment.common.Constants
import com.assignment.data.api.ApiService
import com.assignment.data.repositories.local.LocalDatabase
import com.assignment.data.repositories.local.LocalRepo
import com.assignment.data.repositories.local.LocalRepoImpl
import com.assignment.data.repositories.remote.RemoteRepo
import com.assignment.data.repositories.remote.RemoteRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Modules providing implementation of methods required for HILT or dependency injection
 */
@Module
@InstallIn(ApplicationComponent::class)
object Modules {

    /**
     * Provides Retrofit Object
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides ApiService Object based on Retrofit Object
     */
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Provides RemoteRepo Object based on ApiService Object
     */
    @Singleton
    @Provides
    fun provideRemoteRepo(apiService: ApiService): RemoteRepo {
        return RemoteRepoImpl(apiService)
    }

    /**
     * Provides LocalDatabase Object
     */
    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Provides LocalRepo Object based on LocalDatabase Object
     */
    @Singleton
    @Provides
    fun provideLocalRepo(localDatabase: LocalDatabase): LocalRepo {
        return LocalRepoImpl(localDatabase.getDao())
    }

}