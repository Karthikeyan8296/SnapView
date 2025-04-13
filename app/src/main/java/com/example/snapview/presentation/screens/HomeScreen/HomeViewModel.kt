package com.example.snapview.presentation.screens.HomeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapview.data.mapper.toDomainModelList
import com.example.snapview.data.remote.dto.UnsplashImageDTO
import com.example.snapview.di.AppModule
import com.example.snapview.domain.model.UnsplashImage
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

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
            val response = AppModule.retrofitService.getEditorialFeedImages()
            //mapper function to map the dto to domain model
            images = response.toDomainModelList()
        }
    }
}