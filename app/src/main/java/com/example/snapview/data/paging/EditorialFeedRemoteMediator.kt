package com.example.snapview.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.snapview.data.local.SnapViewDatabase
import com.example.snapview.data.local.entity.UnsplashImageEntity
import com.example.snapview.data.local.entity.UnsplashRemoteKeys
import com.example.snapview.data.mapper.toEntityList
import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.data.util.Constants.ITEMS_PER_PAGE

@OptIn(ExperimentalPagingApi::class)
class EditorialFeedRemoteMediator(
    private val APIservice: UnsplashAPIService,
    private val dataBase: SnapViewDatabase
) : RemoteMediator<Int, UnsplashImageEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private val editorialFeedDAO = dataBase.editorialFeedDAO()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageEntity>
    ): MediatorResult {
        try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = APIservice.getEditorialFeedImagesOriginal(
                page = currentPage, perPage = ITEMS_PER_PAGE
            )
            val endOffPaginationReached = response.isEmpty()
            val prevPage = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
            val nextPage = if (endOffPaginationReached) null else currentPage + 1

            dataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    editorialFeedDAO.deleteAllEditorialFeedImage()
                    editorialFeedDAO.deleteAllRemoteKeys()
                }
                val remoteKeys = response.map { unsplashImageDTO ->
                    UnsplashRemoteKeys(
                        id = unsplashImageDTO.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                editorialFeedDAO.insertRemoteKeys(remoteKeys)
                editorialFeedDAO.insertEditorialFeedImage(response.toEntityList())
            }
            return MediatorResult.Success(endOffPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                editorialFeedDAO.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unSplashImage ->
                editorialFeedDAO.getRemoteKeys(id = unSplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unSplashImage ->
                editorialFeedDAO.getRemoteKeys(id = unSplashImage.id)
            }
    }
}