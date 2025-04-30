package com.example.snapview.presentation.screens.FullImageScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.components.LoadingCard

@Composable
fun FullImageScreen(image: UnsplashImage?, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        var isLoading by remember { mutableStateOf(true) }
        var isError by remember { mutableStateOf(false) }

        val imageLoader = rememberAsyncImagePainter(
            model = image?.imageUrlRaw,
            onState = { imageState ->
                isLoading = imageState is AsyncImagePainter.State.Loading
                isError = imageState is AsyncImagePainter.State.Error
            }
        )

        if (isLoading || isError) {
            LoadingCard(size = 60.dp)
        } else {
            Image(
                painter = imageLoader,
                contentDescription = null
            )
        }
    }
}