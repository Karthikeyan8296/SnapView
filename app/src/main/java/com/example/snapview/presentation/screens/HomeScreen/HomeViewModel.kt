package com.example.snapview.presentation.screens.HomeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository,
) : ViewModel() {

    //state
    var images: List<UnsplashImage> by mutableStateOf(emptyList())
        private set

    //we don't have any getImages functions in the ui screen(like button to call that functions),
    // so it will automatically call this function and show the images
    init {
        getImages()
    }

    //function for that state
    private fun getImages() {
        viewModelScope.launch {
            val response = repository.getEditorialFeedImages()
            images = response
        }
    }
}