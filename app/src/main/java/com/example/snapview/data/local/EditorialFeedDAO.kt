package com.example.snapview.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.snapview.data.local.entity.UnsplashImageEntity
import com.example.snapview.data.local.entity.UnsplashRemoteKeys

@Dao
interface EditorialFeedDAO {

    @Query("SELECT * FROM image_table")
    fun getAllEditorialFeedImages(): PagingSource<Int, UnsplashImageEntity>

    @Upsert
    suspend fun insertEditorialFeedImage(images: List<UnsplashImageEntity>)

    @Query("DELETE FROM image_table")
    suspend fun deleteAllEditorialFeedImage()

    @Query("SELECT * FROM remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Upsert
    suspend fun insertRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}