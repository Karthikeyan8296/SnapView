package com.example.snapview.presentation.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.components.ImagesVerticalGrid
import com.example.snapview.presentation.components.TopAppBar
import com.example.snapview.presentation.components.ZoomImageCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    images: List<UnsplashImage>,
    onClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    onFabClick: () -> Unit,
) {
    var showImagePreview by remember { mutableStateOf(false) }
    var ActiveImage by remember { mutableStateOf<UnsplashImage?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
            ImagesVerticalGrid(
                images = images,
                onImageClick = onClick,
                onImageDragStart = { image ->
                    ActiveImage = image
                    showImagePreview = true
                },
                onImageDragEnd = {
                    showImagePreview = false
                }
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            onClick = { onFabClick() }
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Fav",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        //Image card zoom View
        ZoomImageCard(
            isVisible = showImagePreview,
            image = ActiveImage,
        )
    }


}