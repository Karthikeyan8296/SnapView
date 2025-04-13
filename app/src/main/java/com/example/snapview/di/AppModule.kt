package com.example.snapview.di

import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.data.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object AppModule {
    //we will create the objects of the unsplash api

    //it will ignore that unnecessary fields we deleted
    val contentType = "application/json".toMediaType()
    val json = Json { ignoreUnknownKeys = true }

    //retrofit objects
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory(contentType))
        .baseUrl(BASE_URL)
        .build()

    //object for retrofit service
    val retrofitService: UnsplashAPIService by lazy {
        retrofit.create(UnsplashAPIService::class.java)
    }
}