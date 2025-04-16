package com.example.snapview.presentation.navigations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.snapview.presentation.screens.FavoriteScreen.FavoriteScreen
import com.example.snapview.presentation.screens.FullImageScreen.FullImageScreen
import com.example.snapview.presentation.screens.HomeScreen.HomeScreen
import com.example.snapview.presentation.screens.HomeScreen.HomeViewModel
import com.example.snapview.presentation.screens.ProfileScreen.ProfileScreen
import com.example.snapview.presentation.screens.SearchScreen.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController, scrollBehavior: TopAppBarScrollBehavior) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {
            //viewModel
            //val viewModel = viewModel<HomeViewModel>()
            //using dagger hilt
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                images = viewModel.images,
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
            )
        }
        composable<Routes.SearchScreen> {
            SearchScreen()
        }
        composable<Routes.FavoriteScreen> {
            FavoriteScreen()
        }
        //getting the image id with backStackEntry
        composable<Routes.FullImageScreen> { backStackEntry ->
            val imageId = backStackEntry.toRoute<Routes.FullImageScreen>().imageId
            FullImageScreen(
                imageId = imageId
            )
        }
        composable<Routes.ProfileScreen> {
            ProfileScreen()
        }
    }
}