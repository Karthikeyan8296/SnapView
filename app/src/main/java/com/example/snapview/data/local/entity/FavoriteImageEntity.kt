package com.example.snapview.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.snapview.data.util.Constants.FAVORITE_IMAGE_TABLE

//these are the columns
@Entity(tableName = FAVORITE_IMAGE_TABLE)      //this is the table name
data class FavoriteImageEntity(
    @PrimaryKey
    val id: String,         //this will be the primary key for this table
    val imageUrlSmall: String,
    val imageUrlRegular: String,
    val imageUrlRaw: String,
    val photographerName: String,
    val photographerUsername: String,
    val photographerProfileImageUrl: String,
    val photographerProfileLink: String,
    val width: Int,
    val height: Int,
    val description: String?,
)