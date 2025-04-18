package com.example.snapview.di

import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.data.repository.ImageRepositoryImpl
import com.example.snapview.data.util.Constants.BASE_URL
import com.example.snapview.domain.repository.ImageRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //we will create the objects of the unsplash api

    //this will return the value to the provide image repo
    @Provides
    @Singleton
    fun provideUnsplashAPIservice(): UnsplashAPIService {
        //it will ignore that unnecessary fields we deleted
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        //retrofit objects
        val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(UnsplashAPIService::class.java)
    }

    //We are telling the hilt that it need to see this function(repository)
    @Provides
    @Singleton
    //connecting the viewmodel with the repo
    fun provideImageRepository(apiService: UnsplashAPIService): ImageRepository {
        return ImageRepositoryImpl(apiService)
    }
}