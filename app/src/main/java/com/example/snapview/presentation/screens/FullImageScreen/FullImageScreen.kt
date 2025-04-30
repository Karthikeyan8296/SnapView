package com.example.snapview.presentation.screens.FullImageScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.components.LoadingCard
import kotlin.math.max

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FullImageScreen(image: UnsplashImage?, modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints {
            //Zoom functionality
            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }
            val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
                scale = max(scale * zoomChange, 1f)
                val maxY = (constraints.maxHeight * (scale - 1)) / 2
                val maxX = (constraints.maxWidth * (scale - 1)) / 2
                offset = Offset(
                    x = (offset.x + offsetChange.x).coerceIn(-maxX, maxX),
                    y = (offset.y + offsetChange.y).coerceIn(-maxY, maxY)
                )
            }

            //21:00
            var isLoading by remember { mutableStateOf(true) }
            var isError by remember { mutableStateOf(false) }

            val imageLoader = rememberAsyncImagePainter(
                model = image?.imageUrlRaw,
                onState = { imageState ->
                    isLoading = imageState is AsyncImagePainter.State.Loading
                    isError = imageState is AsyncImagePainter.State.Error
                }
            )

            if (isLoading) {
                LoadingCard(size = 60.dp)
            } else {
                Image(
                    painter = imageLoader,
                    contentDescription = null,
                    modifier = Modifier
                        .transformable(transformState)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset.x
                            translationY = offset.y
                        }
                )
            }
        }
    }


}