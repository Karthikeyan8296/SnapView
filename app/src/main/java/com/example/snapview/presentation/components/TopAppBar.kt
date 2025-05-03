package com.example.snapview.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.snapview.R
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.ui.theme.InterFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String = "SnapView",
    onSearchClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun FullImageViewTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    image: UnsplashImage?,
    onPhotographerNameClick: (String) -> Unit,
    onDownloadClick: () -> Unit,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            AsyncImage(
                model = image?.photographerProfileImageUrl,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                contentDescription = "Photographer Profile Image"
            )
            Spacer(Modifier.width(10.dp))
            Column(
                modifier = Modifier.clickable {
                    //pass the profile link
                    //if the image is not null, then send the link
                    image?.let { onPhotographerNameClick(it.photographerProfileLink) }
                }
            ) {
                Text(
                    text = image?.photographerName ?: "Anonymous",
                    fontFamily = InterFontFamily,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = image?.photographerUsername ?: "HappyUser",
                    fontFamily = InterFontFamily,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { onDownloadClick() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_download),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Download",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}