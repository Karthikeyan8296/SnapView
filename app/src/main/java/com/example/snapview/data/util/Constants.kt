package com.example.snapview.data.util

import com.example.snapview.BuildConfig


object Constants {
    const val API_ACCESS_KEY = BuildConfig.UNSPLASH_API_ACCESS
    const val BASE_URL = "https://api.unsplash.com/"

    const val ITEMS_PER_PAGE = 10

    const val FAVORITE_IMAGE_TABLE = "favorite_image_table"
    const val SNAP_VIEW_DATABASE = "Unsplash_image_db"
}