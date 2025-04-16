package com.example.snapview.domain.repository

import com.example.snapview.domain.model.UnsplashImage

interface ImageRepository {
    suspend fun getEditorialFeedImages(): List<UnsplashImage>
}