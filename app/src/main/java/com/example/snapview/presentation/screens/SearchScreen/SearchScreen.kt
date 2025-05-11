package com.example.snapview.presentation.screens.SearchScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.components.ImagesVerticalGrid
import com.example.snapview.presentation.components.TopAppBar
import com.example.snapview.presentation.components.ZoomImageCard
import com.example.snapview.presentation.util.SnackBarEvent
import com.example.snapview.presentation.util.searchKeyword
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onImageClick: (String) -> Unit,
    snackBarEvent: Flow<SnackBarEvent>,
    snackBarState: SnackbarHostState,
    searchedImages: LazyPagingItems<UnsplashImage>,
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit,
    onToggleFavoriteStatus: (UnsplashImage) -> Unit,
    favImageID: List<String>
) {
    var showImagePreview by remember { mutableStateOf(false) }
    var ActiveImage by remember { mutableStateOf<UnsplashImage?>(null) }

    var query by remember { mutableStateOf("") }
    val focusReq = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isSuggestionChipVisible by remember { mutableStateOf(false) }


    //focus requester for keyboard
    LaunchedEffect(key1 = Unit) {
        delay(500)
        focusReq.requestFocus()
    }

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
            SearchBar(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .focusRequester(focusReq)
                    .onFocusChanged { isSuggestionChipVisible = it.isFocused },
                query = query,
                onQueryChange = { query = it },
                placeholder = { Text(text = "Search") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (query.isNotEmpty()) query = "" else onBackClick()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close"
                        )
                    }
                },
                leadingIcon = {
                    IconButton(
                        onClick = { onSearch(query) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                onSearch = {
                    onSearch(query)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                active = false,
                onActiveChange = {},
                //searching content
                content = {}
            )
            AnimatedVisibility(visible = isSuggestionChipVisible) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(searchKeyword) { keyword ->
                        SuggestionChip(
                            onClick = {
                                query = keyword
                                onSearch(query)
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            },
                            label = { Text(text = keyword) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            ImagesVerticalGrid(
                images = searchedImages,
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
    }
}
