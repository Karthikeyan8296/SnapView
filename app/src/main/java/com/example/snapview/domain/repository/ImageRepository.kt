package com.example.snapview.domain.repository

import androidx.paging.PagingData
import com.example.snapview.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    //In this repository we will only use the models created in the models package

    //Suspend fun -> If we want to data in oneGo, then we use this suspend fun (like id, or single value)
    //fun -> if we want the data continuously, then we use this fun with Flow return (more data like rendering list and continuously)

    //list of images
//    suspend fun getEditorialFeedImages(): List<UnsplashImage>

    fun getEditorialFeedImageOriginal(): Flow<PagingData<UnsplashImage>>

    //get the single image
    suspend fun getImage(imageId: String): UnsplashImage

    //Search an image
    fun searchImage(query: String): Flow<PagingData<UnsplashImage>>

    //toggling the favImage to send and unSend to fav
    suspend fun toggleFavoriteStatus(image: UnsplashImage)

    //getting the id to send to fav
    fun getFavoriteImageID(): Flow<List<String>>

    //get all favorite images
    fun getAllFavoriteImages(): Flow<PagingData<UnsplashImage>>
}