package com.example.snapview.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.snapview.data.local.entity.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImagesDAO {

    @Query("SELECT * FROM favorite_image_table")
    fun getFavoriteImages(): PagingSource<Int, FavoriteImageEntity>

    @Upsert
    suspend fun insertFavoriteImage(image: FavoriteImageEntity)

    @Delete
    suspend fun deleteFavoriteImage(image: FavoriteImageEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_image_table WHERE id = :imageId)")
    suspend fun isImageFavorite(imageId: String): Boolean

    @Query("SELECT id from favorite_image_table")
    fun getFavoriteImageById(): Flow<List<String>>
}