package com.example.snapview.presentation.screens.SearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.snapview.domain.model.UnsplashImage
import com.example.snapview.domain.repository.ImageRepository
import com.example.snapview.presentation.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    //SnackBar
    private val _snackBarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.receiveAsFlow()

    private val _searchImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchImages = _searchImages

    fun searchImages(query: String) {
        viewModelScope.launch {
            try {
                repository
                    .searchImage(query)
                    .cachedIn(viewModelScope)
                    .collect { _searchImages.value = it }
            } catch (e: Exception) {
                _snackBarEvent.send(
                    SnackBarEvent(message = "Something went wrong. ${e.message}")
                )
            }
        }
    }

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
        repository.getFavoriteImageID().catch { exception ->
            _snackBarEvent.send(
                SnackBarEvent(message = "Something went wrong. ${exception.message}")
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}