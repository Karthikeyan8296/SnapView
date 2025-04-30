package com.example.snapview.data.repository

import com.example.snapview.data.mapper.toDomainModel
import com.example.snapview.data.mapper.toDomainModelList
import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.domain.repository.ImageRepository

class ImageRepositoryImpl(private val unSplashAPI: UnsplashAPIService) : ImageRepository {
    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        //mapper function to map the dto to domain model
        return unSplashAPI.getEditorialFeedImages().toDomainModelList()
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        //mapper function to map the dto to domain model
        return unSplashAPI.getImage(imageId).toDomainModel()
    }
}