package com.example.snapview.data.mapper

import com.example.snapview.data.local.entity.FavoriteImageEntity
import com.example.snapview.data.remote.dto.UnsplashImageDTO
import com.example.snapview.domain.model.UnsplashImage

//DTO
//mapping the dto to domain model
//mapper for single image
fun UnsplashImageDTO.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        photographerName = this.user.name,
        photographerUsername = this.user.username,
        photographerProfileImageUrl = this.user.profileImage.small,
        photographerProfileLink = this.user.links.html,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

//mapper for whole list
fun List<UnsplashImageDTO>.toDomainModelList(): List<UnsplashImage> {
    return this.map { it.toDomainModel() }
}

//DAO
//changing the Domain model to DAO(Entity)
fun UnsplashImage.toFavoriteImageEntity(): FavoriteImageEntity {
    return FavoriteImageEntity(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImageUrl = this.photographerProfileImageUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = this.description
    )
}