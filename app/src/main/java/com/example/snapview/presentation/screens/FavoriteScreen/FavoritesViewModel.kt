package com.example.snapview.presentation.screens.FavoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.domain.repository.ImageRepository
import com.example.snapview.presentation.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: ImageRepository) :
    ViewModel() {
    //SnackBar
    private val _snackBarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.receiveAsFlow()

    val getFavoriteImages: StateFlow<PagingData<UnsplashImage>> =
        repository.getAllFavoriteImages()
            .catch { exception ->
                _snackBarEvent.send(
                    SnackBarEvent(message = "Something went wrong. ${exception.message}")
                )
            }.cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PagingData.empty()
            )

    fun toggleFavoriteStatus(image: UnsplashImage) {
        viewModelScope.launch {
            try {
                repository.toggleFavoriteStatus(image)
            } catch (e: Exception) {
                _snackBarEvent.send(
                    SnackBarEvent(message = "Something went wrong. ${e.message}")
                )
            }
        }
    }

    //getting the favImages id from the db
    val getFavoriteImagesID: StateFlow<List<String>> =
        repository.getFavoriteImageID()
            .catch { exception ->
                _snackBarEvent.send(
                    SnackBarEvent(message = "Something went wrong. ${exception.message}")
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}