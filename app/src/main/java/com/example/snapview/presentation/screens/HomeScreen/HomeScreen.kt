package com.example.snapview.presentation.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.components.HomeImagesVerticalGrid
import com.example.snapview.presentation.components.ImagesVerticalGrid
import com.example.snapview.presentation.components.ImagesVerticalGridNormal
import com.example.snapview.presentation.components.TopAppBar
import com.example.snapview.presentation.components.ZoomImageCard
import com.example.snapview.presentation.util.SnackBarEvent
import com.example.snapview.presentation.util.searchKeyword
import com.example.snapview.ui.theme.InterFontFamily
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    imagesOriginal: LazyPagingItems<UnsplashImage>,
    onClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    onFabClick: () -> Unit,
    snackBarEvent: Flow<SnackBarEvent>,
    snackBarState: SnackbarHostState,
    onToggleFavoriteStatus: (UnsplashImage) -> Unit,
    favImageID: List<String>
) {
    var showImagePreview by remember { mutableStateOf(false) }
    var ActiveImage by remember { mutableStateOf<UnsplashImage?>(null) }

    //SnackBar
    LaunchedEffect(key1 = true) {
        snackBarEvent.collect { event ->
            snackBarState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            TopAppBar(
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )

            HomeImagesVerticalGrid(
                images = imagesOriginal,
                onImageClick = onClick,
                onImageDragStart = { image ->
                    ActiveImage = image
                    showImagePreview = true
                },
                onImageDragEnd = {
                    showImagePreview = false
                },
                favImageID = favImageID,
                isFavClick = onToggleFavoriteStatus
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