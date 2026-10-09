package com.mauricioliveira.feature.albumdetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauricioliveira.core.domain.AlbumRepository
import com.mauricioliveira.core.ui.toErrorMessageRes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class AlbumDetailViewModel(
    private val albumId: String,
    private val repository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumDetailUiState>(AlbumDetailUiState.Loading)
    val uiState: StateFlow<AlbumDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun retry() = load()

    private fun load() {
        _uiState.value = AlbumDetailUiState.Loading
        viewModelScope.launch(dispatcher) {
            _uiState.value = try {
                AlbumDetailUiState.Success(repository.getAlbum(albumId))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                AlbumDetailUiState.Error(e.toErrorMessageRes())
            }
        }
    }
}