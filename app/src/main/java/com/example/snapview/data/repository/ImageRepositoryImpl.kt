package com.example.snapview.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.snapview.data.local.SnapViewDatabase
import com.example.snapview.data.mapper.toDomainModel
import com.example.snapview.data.mapper.toDomainModelList
import com.example.snapview.data.mapper.toEntityList
import com.example.snapview.data.mapper.toFavoriteImageEntity
import com.example.snapview.data.paging.EditorialFeedRemoteMediator
import com.example.snapview.data.paging.SearchPagingSource
import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.data.util.Constants.ITEMS_PER_PAGE
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryImpl(
    private val unSplashAPI: UnsplashAPIService,
    private val database: SnapViewDatabase
) : ImageRepository {

    //check if the image is already in fav
    private val favoriteImageDAO = database.favoriteImagesDao()

    private val editorialImageDAO = database.editorialFeedDAO()

    //default setup
//    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
//        //mapper function to map the dto to domain model
//        return unSplashAPI.getEditorialFeedImages().toDomainModelList()
//    }

    override fun getEditorialFeedImageOriginal(): Flow<PagingData<UnsplashImage>> {
        //paging setup
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = EditorialFeedRemoteMediator(unSplashAPI, database),
            pagingSourceFactory = { editorialImageDAO.getAllEditorialFeedImages() }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
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

    override fun getAllFavoriteImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { favoriteImageDAO.getFavoriteImages() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }
}