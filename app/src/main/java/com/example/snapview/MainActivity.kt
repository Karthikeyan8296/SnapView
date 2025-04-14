package com.example.snapview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.snapview.presentation.navigations.NavGraph
import com.example.snapview.presentation.screens.HomeScreen.HomeScreen
import com.example.snapview.presentation.screens.HomeScreen.HomeViewModel
import com.example.snapview.ui.theme.SnapViewTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            SnapViewTheme {
                //scrollBehavior for the topAppBar
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                //navController
                val navController = rememberNavController()

                //Main Content
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    NavGraph(navController = navController, scrollBehavior = scrollBehavior)
                }

            }
        }
    }
}