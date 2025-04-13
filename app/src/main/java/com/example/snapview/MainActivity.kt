package com.example.snapview

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapview.presentation.screens.HomeScreen.HomeScreen
import com.example.snapview.presentation.screens.HomeScreen.HomeViewModel
import com.example.snapview.ui.theme.SnapViewTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnapViewTheme {
                //viewModel
                val viewModel = viewModel<HomeViewModel>()

                //scrollBehavior for the topAppBar
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                //MainContent
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) { paddingValues ->
                    HomeScreen(
                        paddingValues = paddingValues,
                        images = viewModel.images,
                        scrollBehavior = scrollBehavior,
                        onClick = {},
                        onSearchClick = {},
                        onFabClick = {},
                    )
                }

            }
        }
    }
}