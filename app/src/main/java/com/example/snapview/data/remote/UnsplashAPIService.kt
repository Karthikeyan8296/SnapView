package com.example.snapview.data.remote

import com.example.snapview.data.util.Constants.API_ACCESS_KEY
import retrofit2.http.GET
import retrofit2.http.Headers

interface UnsplashAPIService {
    //functions for api calls

    //public auth access key
    @Headers("Authorization: Client-ID $API_ACCESS_KEY")
    @GET("/photos")
    suspend fun getEditorialFeedImages(): String
}