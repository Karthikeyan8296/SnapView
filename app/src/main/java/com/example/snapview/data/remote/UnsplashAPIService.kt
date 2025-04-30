package com.example.snapview.data.remote

import com.example.snapview.data.remote.dto.UnsplashImageDTO
import com.example.snapview.data.util.Constants.API_ACCESS_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UnsplashAPIService {
    //functions for api calls

    //public auth access key
    @Headers("Authorization: Client-ID $API_ACCESS_KEY")
    @GET("/photos")
    suspend fun getEditorialFeedImages(): List<UnsplashImageDTO>

    @Headers("Authorization: Client-ID $API_ACCESS_KEY")
    @GET("/photos/{id}")
    suspend fun getImage(
        @Path("id") imageId: String
    ): UnsplashImageDTO
}