package com.example.snapview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.snapview.data.local.entity.FavoriteImageEntity
import com.example.snapview.data.local.entity.UnsplashImageEntity
import com.example.snapview.data.local.entity.UnsplashRemoteKeys

@Database(
    entities = [FavoriteImageEntity::class, UnsplashImageEntity::class, UnsplashRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class SnapViewDatabase : RoomDatabase() {
    abstract fun favoriteImagesDao(): FavoriteImagesDAO

    abstract fun editorialFeedDAO(): EditorialFeedDAO
}