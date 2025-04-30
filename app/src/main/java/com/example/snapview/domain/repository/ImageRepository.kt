package com.example.snapview.domain.repository

import com.example.snapview.domain.model.UnsplashImage

interface ImageRepository {
    //In this repository we will only use the models created in the models package

    //list of images
    suspend fun getEditorialFeedImages(): List<UnsplashImage>

    //get the single image
    suspend fun getImage(imageId: String): UnsplashImage
}