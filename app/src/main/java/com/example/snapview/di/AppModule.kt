package com.example.snapview.di

import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.data.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object AppModule {
    //we will create the objects of the unsplash api

    //retrofit objects
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    //object for retrofit service
    val retrofitService : UnsplashAPIService by lazy {
        retrofit.create(UnsplashAPIService::class.java)
    }
}