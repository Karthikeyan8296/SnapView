package com.example.snapview.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.snapview.data.util.Constants.UNSPLASH_REMOTE_KEY_TABLE

@Entity(tableName = UNSPLASH_REMOTE_KEY_TABLE)
data class UnsplashRemoteKeys(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
