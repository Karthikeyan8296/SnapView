package com.example.snapview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.snapview.domain.model.NetworkStatus
import com.example.snapview.domain.repository.NetworkConnectivityObserver
import com.example.snapview.presentation.components.NetworkStatusBar
import com.example.snapview.presentation.navigations.NavGraph
import com.example.snapview.presentation.navigations.Routes
import com.example.snapview.presentation.screens.OnBoardingScreen.OnBoardingManager
import com.example.snapview.ui.theme.SnapViewTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var connectivityObserver: NetworkConnectivityObserver

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            //network status
            val status by connectivityObserver.networkStatus.collectAsState()

            var showMessage by rememberSaveable { mutableStateOf(false) }
            var message by rememberSaveable { mutableStateOf("") }
            var backgroundColor by remember { mutableStateOf(Color.Red) }

            LaunchedEffect(key1 = status) {
                when (status) {
                    NetworkStatus.Connected -> {
                        message = "Network Connected"
                        backgroundColor = Color(0xFF19B661)
                        delay(2000)
                        showMessage = false
                    }

                    NetworkStatus.Disconnected -> {
                        showMessage = true
                        message = "No Internet Connection"
                        backgroundColor = Color.Red
                    }
                }
            }

            //onBoarding manager
            val onBoardingManager = OnBoardingManager(applicationContext)

            SnapViewTheme {
                //scrollBehavior for the topAppBar UI
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

                //navController
                val navController = rememberNavController()

                //SnackBar state
                val snackBarState = SnackbarHostState()

                //Onboarding check
                val splashScreen = installSplashScreen()
                val onboardingState by produceState<Boolean?>(initialValue = null) {
                    value = onBoardingManager.isOnboardingCompleted.first()
                }

                // Keep splash screen until onboardingState is loaded
                splashScreen.setKeepOnScreenCondition { onboardingState == null }

                // Only continue once onboardingState is loaded
                onboardingState?.let { isCompleted ->
                    val startDestinationOriginal =
                        if (isCompleted) Routes.HomeScreen else Routes.OnBoardingScreen

                    //Main Content
                    Scaffold(
                        snackbarHost = { SnackbarHost(hostState = snackBarState) },
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        bottomBar = {
                            NetworkStatusBar(
                                showMessageBar = showMessage, message = message,
                                backgroundColor = backgroundColor,
                            )
                        }
                    ) { paddingValues ->
                        NavGraph(
                            onBoardingManager = onBoardingManager,
                            startDestination = startDestinationOriginal,
                            paddingValues = paddingValues,
                            navController = navController,
                            scrollBehavior = scrollBehavior,
                            snackBarHostState = snackBarState
                        )
                    }
                }
            }
        }
    }
}