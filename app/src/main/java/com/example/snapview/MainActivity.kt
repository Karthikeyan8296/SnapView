package com.example.snapview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapview.presentation.screens.HomeScreen.HomeScreen
import com.example.snapview.presentation.screens.HomeScreen.HomeViewModel
import com.example.snapview.ui.theme.InterFontFamily
import com.example.snapview.ui.theme.SnapViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnapViewTheme {
                val viewModel = viewModel<HomeViewModel>()
                HomeScreen(images = viewModel.images)
            }
        }
    }
}