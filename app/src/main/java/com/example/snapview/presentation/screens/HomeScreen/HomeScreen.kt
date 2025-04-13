package com.example.snapview.presentation.screens.HomeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.snapview.data.remote.dto.UnsplashImageDTO
import com.example.snapview.domain.model.UnsplashImage

@Composable
fun HomeScreen(images: List<UnsplashImage>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        images.forEach { image ->
            Text(text = image.id)
            Text(text = image.photographerName)
            Text(text = "${image.height} " + " x " + " ${image.width}")
            Text(text = image.imageUrlSmall, color = Color.Blue)
            HorizontalDivider()
        }
    }
}