package com.example.snapview.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.snapview.domain.model.UnsplashImage

@Composable
fun ImagesVerticalGridNormal(
    modifier: Modifier = Modifier,
    images: List<UnsplashImage?>,
    favImageID: List<String>,
    onImageClick: (String) -> Unit,
    onImageDragStart: (UnsplashImage?) -> Unit,
    onImageDragEnd: (UnsplashImage?) -> Unit,
    isFavClick: (UnsplashImage) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .then(modifier),
        contentPadding = PaddingValues(8.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { image ->
            ImageCard(
                image = image,
                modifier = Modifier
                    .clickable {
                        image?.id?.let { onImageClick(it) }
                    }
                    .pointerInput(Unit) {//gesture for clickable
                        detectDragGesturesAfterLongPress(
                            onDragStart = { onImageDragStart(image) },
                            onDragCancel = { onImageDragEnd(image) },
                            onDragEnd = { onImageDragEnd(image) },
                            onDrag = { _, _ -> }
                        )
                    },
                isFavorite = favImageID.contains(image?.id),
                onFavClick = { image?.let { isFavClick(it) } }
            )
        }
    }
}