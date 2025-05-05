package com.example.snapview.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageDTO(
    val id: String,
    val width: Int,
    val height: Int,
    val description: String?,
    val likes: Int,
    val urls: UrlsDTO,
    val user: UserDTO,
)

@Serializable
data class UrlsDTO(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String,
)

@Serializable
data class UserDTO(
    val id: String,
    val bio: String?,
    @SerialName("instagram_username")
    val instagramUsername: String?,
    val links: UserLinksDTO,
    val location: String?,
    val name: String,
    @SerialName("portfolio_url")
    val portfolioURL: String?,
    @SerialName("profile_image")
    val profileImage: ProfileImageDTO,
    @SerialName("total_collections")
    val totalCollections: Int?,
    @SerialName("total_likes")
    val totalLikes: Int?,
    @SerialName("total_photos")
    val totalPhotos: Int?,
    @SerialName("twitter_username")
    val twitterUsername: String?,
    val username: String,
)

@Serializable
data class ProfileImageDTO(
    val large: String,
    val medium: String,
    val small: String,
)

@Serializable
data class UserLinksDTO(
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String,
)