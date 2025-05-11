package com.example.snapview.presentation.screens.FavoriteScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.snapview.R
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.components.ImagesVerticalGrid
import com.example.snapview.presentation.components.TopAppBar
import com.example.snapview.presentation.components.ZoomImageCard
import com.example.snapview.presentation.util.SnackBarEvent
import com.example.snapview.presentation.util.searchKeyword
import com.example.snapview.ui.theme.InterFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    onImageClick: (String) -> Unit,
    snackBarEvent: Flow<SnackBarEvent>,
    snackBarState: SnackbarHostState,
    favoriteImages: LazyPagingItems<UnsplashImage>,
    onBackClick: () -> Unit,
    onToggleFavoriteStatus: (UnsplashImage) -> Unit,
    favImageID: List<String>,
    onSearchClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = "Favorites",
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
            ImagesVerticalGrid(
                images = favoriteImages,
                onImageClick = onImageClick,
                onImageDragStart = { image ->
                    ActiveImage = image
                    showImagePreview = true
                },
                onImageDragEnd = {
                    showImagePreview = false
                },
                isFavClick = onToggleFavoriteStatus,
                favImageID = favImageID
            )
        }
        //Image card zoom View
        ZoomImageCard(
            isVisible = showImagePreview,
            image = ActiveImage,
        )

//        if (favoriteImages.itemCount == 0) {
//            EmptyState()
//        }
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.logo_bg), contentDescription = "null"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Saved Images",
            fontFamily = InterFontFamily,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Images you save will be sored here...",
            fontFamily = InterFontFamily,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}