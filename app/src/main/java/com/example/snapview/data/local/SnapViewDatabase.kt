package com.example.snapview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.snapview.data.local.entity.FavoriteImageEntity

@Database(
    entities = [FavoriteImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SnapViewDatabase : RoomDatabase() {
    abstract fun favoriteImagesDao(): FavoriteImagesDAO
}