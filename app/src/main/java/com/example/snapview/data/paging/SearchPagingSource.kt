package com.example.snapview.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.snapview.data.remote.UnsplashAPIService
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.data.mapper.toDomainModelList

//paging source
class SearchPagingSource(
    private val query: String,
    private val unsplashAPI: UnsplashAPIService
) : PagingSource<Int, UnsplashImage>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }

    //here we need to define - how the paging3 lib will load the data
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = unsplashAPI.searchImage(
                query = query,
                page = currentPage,
                perPage = params.loadSize
            )
            val endOfPaginationReached = response.images.isEmpty()
            LoadResult.Page(
                data = response.images.toDomainModelList(),
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (endOfPaginationReached) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}