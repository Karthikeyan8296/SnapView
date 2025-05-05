package com.example.snapview.domain.repository

import androidx.paging.PagingData
import com.example.snapview.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    //In this repository we will only use the models created in the models package

    //list of images
    suspend fun getEditorialFeedImages(): List<UnsplashImage>

    //get the single image
    suspend fun getImage(imageId: String): UnsplashImage

    fun searchImage(query: String): Flow<PagingData<UnsplashImage>>
}