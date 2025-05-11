package com.example.snapview.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.util.searchKeyword
import com.example.snapview.ui.theme.InterFontFamily

@Composable
fun HomeImagesVerticalGrid(
    modifier: Modifier = Modifier,
    images: LazyPagingItems<UnsplashImage>,
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
        item(span = StaggeredGridItemSpan.FullLine) {
            Text(
                text = "Your Perfect Screen, \nOne Tap Away! ❤\uFE0F\u200D\uD83D\uDD25",
                textAlign = TextAlign.Left,
                fontFamily = InterFontFamily,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 38.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(count = images.itemCount) { index ->
            val image = images[index]
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