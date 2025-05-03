package com.example.snapview.presentation.screens.FullImageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.domain.repository.Downloader
import com.example.snapview.domain.repository.ImageRepository
import com.example.snapview.presentation.navigations.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullImageViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val downloader: Downloader,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    //getting the image id from the navGraph directly
    private val imageId = savedStateHandle.toRoute<Routes.FullImageScreen>().imageId

    //state
    var image: UnsplashImage? by mutableStateOf(null)
        private set

    init {
        getImage()
    }

    //function for that state
    private fun getImage() {
        viewModelScope.launch {
            try {
                val response = repository.getImage(imageId = imageId)
                image = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //download function
    fun downloadImg(url: String, fileName: String?) {
        viewModelScope.launch {
            try {
                downloader.downloadFile(url, fileName)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}