package com.example.snapview.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageSearchDTO(
    @SerialName("results")
    val images: List<UnsplashImageDTO>,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
)