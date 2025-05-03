package com.example.snapview.presentation.screens.FullImageScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.animateZoomBy
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.presentation.components.DownloadBottomSheet
import com.example.snapview.presentation.components.FullImageViewTopBar
import com.example.snapview.presentation.components.ImageDownloadOption
import com.example.snapview.presentation.components.LoadingCard
import com.example.snapview.presentation.util.rememberWindowInsetsController
import com.example.snapview.presentation.util.toggleStatusBar
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FullImageScreen(
    image: UnsplashImage?,
    onBackClick: () -> Unit,
    onPhotographerNameClick: (String) -> Unit,
    onImgDownloadClick: (String, String?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var showBars by remember { mutableStateOf(false) }
    val windowInsetsController = rememberWindowInsetsController()
    val context = LocalContext.current

    //showStatusBar Logic
    LaunchedEffect(key1 = Unit) {
        windowInsetsController.toggleStatusBar(show = showBars)
    }

    //manages the backClick also toggles the status bar
    BackHandler(enabled = !showBars) {
        windowInsetsController.toggleStatusBar(show = true)
        onBackClick()
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isDownloadBottomSheetOpen by remember { mutableStateOf(false) }

    DownloadBottomSheet(
        onDismissReq = { isDownloadBottomSheetOpen = false },
        isOpen = isDownloadBottomSheetOpen,
        sheetState = sheetState,
        onOptionClick = { option ->
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) isDownloadBottomSheetOpen = false
            }
            val url = when (option) {
                ImageDownloadOption.SMALL -> image?.imageUrlSmall
                ImageDownloadOption.MEDIUM -> image?.imageUrlRegular
                ImageDownloadOption.ORIGINAL -> image?.imageUrlRaw
            }
            //if it is not null, then run the download function
            url?.let { url ->
                onImgDownloadClick(url, image?.description?.take(20))
                Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            //Zoom functionality
            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }

            //Double Tap onZoom
            val isImageZoomed: Boolean by remember { derivedStateOf { scale != 1f } }

            val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
                scale = max(scale * zoomChange, 1f)
                val maxY = (constraints.maxHeight * (scale - 1)) / 2
                val maxX = (constraints.maxWidth * (scale - 1)) / 2
                offset = Offset(
                    x = (offset.x + offsetChange.x).coerceIn(-maxX, maxX),
                    y = (offset.y + offsetChange.y).coerceIn(-maxY, maxY)
                )
            }

            var isLoading by remember { mutableStateOf(true) }
            var isError by remember { mutableStateOf(false) }

            val imageLoader = rememberAsyncImagePainter(
                model = image?.imageUrlRaw, onState = { imageState ->
                    isLoading = imageState is AsyncImagePainter.State.Loading
                    isError = imageState is AsyncImagePainter.State.Error
                })

            if (isLoading) {
                LoadingCard(size = 60.dp)
            } else {
                Image(
                    painter = imageLoader,
                    contentDescription = null,
                    modifier = Modifier
                        .transformable(transformState)
                        .combinedClickable(
                            onDoubleClick = {
                                if (isImageZoomed) {
                                    scale = 1f
                                    offset = Offset.Zero
                                } else {
                                    scope.launch {
                                        transformState.animateZoomBy(zoomFactor = 3f)
                                    }
                                }
                            },
                            onClick = {
                                showBars = !showBars
                                windowInsetsController.toggleStatusBar(show = showBars)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() })
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset.x
                            translationY = offset.y
                        }
                )
            }
        }

        FullImageViewTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 40.dp),
            image = image,
            isVisible = showBars,
            onBackClick = { onBackClick() },
            onPhotographerNameClick = onPhotographerNameClick,
            onDownloadClick = { isDownloadBottomSheetOpen = true }
        )
    }
}