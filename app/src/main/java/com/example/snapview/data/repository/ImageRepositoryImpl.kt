package com.example.snapview.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.snapview.data.local.SnapViewDatabase
import com.example.snapview.data.mapper.toDomainModel
import com.example.snapview.data.mapper.toDomainModelList
import com.example.snapview.data.mapper.toFavoriteImageEntity
import com.example.snapview.data.paging.SearchPagingSource
import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.data.util.Constants.ITEMS_PER_PAGE
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class ImageRepositoryImpl(
    private val unSplashAPI: UnsplashAPIService,
    private val database: SnapViewDatabase
) : ImageRepository {

    //check if the image is already in fav
    private val favoriteImageDAO = database.favoriteImagesDao()

    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        //mapper function to map the dto to domain model
        return unSplashAPI.getEditorialFeedImages().toDomainModelList()
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        //mapper function to map the dto to domain model
        return unSplashAPI.getImage(imageId).toDomainModel()
    }

    override fun searchImage(query: String): Flow<PagingData<UnsplashImage>> {
        //paging setup
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { SearchPagingSource(query, unSplashAPI) }
        ).flow
    }

    override suspend fun toggleFavoriteStatus(image: UnsplashImage) {
        val isFavorite = favoriteImageDAO.isImageFavorite(image.id)
        //if it is already fav then delete
        if (isFavorite) {
            favoriteImageDAO.deleteFavoriteImage(image.toFavoriteImageEntity())
        } else {
            favoriteImageDAO.insertFavoriteImage(image.toFavoriteImageEntity())
        }
    }

    override fun getFavoriteImageID(): Flow<List<String>> {
        //calling the fav images from DG
        return favoriteImageDAO.getFavoriteImageById()
    }
}