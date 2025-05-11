package com.example.snapview.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@Composable
fun ImageCarousel(
    imageList: List<Int>, // List of drawable IDs
) {
    val pagerState = rememberPagerState(initialPage = imageList.size / 2) // start from center image

    HorizontalPager(
        count = imageList.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp), // Padding for left/right visibility
        itemSpacing = 12.dp, // Space between pages
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    ) { page ->
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffset).absoluteValue
        val scale = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
        var alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = alpha
                    cameraDistance = 8 * density
                }
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.DarkGray)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageList[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}