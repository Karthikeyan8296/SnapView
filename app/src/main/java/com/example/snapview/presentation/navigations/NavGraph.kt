package com.example.snapview.presentation.navigations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.snapview.presentation.screens.ProfileScreen.ProfileScreen
import com.example.snapview.presentation.screens.FavoriteScreen.FavoriteScreen
import com.example.snapview.presentation.screens.FavoriteScreen.FavoritesViewModel
import com.example.snapview.presentation.screens.FullImageScreen.FullImageScreen
import com.example.snapview.presentation.screens.FullImageScreen.FullImageViewModel
import com.example.snapview.presentation.screens.HomeScreen.HomeScreen
import com.example.snapview.presentation.screens.HomeScreen.HomeViewModel
import com.example.snapview.presentation.screens.OnBoardingScreen.OnBoardingScreen
import com.example.snapview.presentation.screens.SearchScreen.SearchScreen
import com.example.snapview.presentation.screens.SearchScreen.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    snackBarHostState: SnackbarHostState,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Routes.OnBoardingScreen
    ) {
        composable<Routes.OnBoardingScreen> {
            OnBoardingScreen(paddingValues = paddingValues)
        }

        composable<Routes.HomeScreen> {
            //viewModel
            //val viewModel = viewModel<HomeViewModel>()
            //using dagger hilt
            val viewModel: HomeViewModel = hiltViewModel()
            val favImageID by viewModel.getFavoriteImagesID.collectAsStateWithLifecycle()
            HomeScreen(
                scrollBehavior = scrollBehavior,
                //passing the image id
                onClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId = imageId))
                },
                onSearchClick = {
                    navController.navigate(Routes.SearchScreen)
                },
                onFabClick = {
                    navController.navigate(Routes.FavoriteScreen)
                },
                snackBarEvent = viewModel.snackBarEvent,
                snackBarState = snackBarHostState,
                onToggleFavoriteStatus = { viewModel.toggleFavoriteStatus(it) },
                favImageID = favImageID,
                imagesOriginal = viewModel.imagesOriginal.collectAsLazyPagingItems()
            )
        }
        composable<Routes.SearchScreen> {
            val viewModel: SearchViewModel = hiltViewModel()
            val favImageID by viewModel.getFavoriteImagesID.collectAsStateWithLifecycle()
            SearchScreen(
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId = imageId))
                },
                onBackClick = { navController.popBackStack() },
                snackBarEvent = viewModel.snackBarEvent,
                snackBarState = snackBarHostState,
                searchedImages = viewModel.searchImages.collectAsLazyPagingItems(),
                onSearch = { viewModel.searchImages(it) },
                onToggleFavoriteStatus = { viewModel.toggleFavoriteStatus(it) },
                favImageID = favImageID
            )
        }
        composable<Routes.FavoriteScreen> {
            val viewModel: FavoritesViewModel = hiltViewModel()
            val favImageID by viewModel.getFavoriteImagesID.collectAsStateWithLifecycle()
            FavoriteScreen(
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId = imageId))
                },
                snackBarEvent = viewModel.snackBarEvent,
                snackBarState = snackBarHostState,
                favoriteImages = viewModel.getFavoriteImages.collectAsLazyPagingItems(),
                onBackClick = {
                    navController.navigateUp()
                },
                onToggleFavoriteStatus = { viewModel.toggleFavoriteStatus(it) },
                favImageID = favImageID,
                onSearchClick = {
                    navController.navigate(Routes.SearchScreen)
                },
                scrollBehavior = scrollBehavior
            )
        }
        //getting the image id with backStackEntry
        composable<Routes.FullImageScreen> {
//            backStackEntry ->
//            val imageId = backStackEntry.toRoute<Routes.FullImageScreen>().imageId
            val viewModel: FullImageViewModel = hiltViewModel()
            FullImageScreen(
                image = viewModel.image,
                onBackClick = {
                    navController.navigateUp()
                },
                onPhotographerNameClick = { photographerProfileLink ->
                    navController.navigate(Routes.ProfileScreen(photographerProfileLink))
                },
                onImgDownloadClick = { url, fileName ->
                    viewModel.downloadImg(url, fileName)
                },
                snackBarEvent = viewModel.snackBarEvent,
                snackBarState = snackBarHostState
            )
        }
        composable<Routes.ProfileScreen> { backStackEntry ->
            val profileLink = backStackEntry.toRoute<Routes.ProfileScreen>().profileLink
            ProfileScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                profileLink = profileLink
            )
        }
    }
}