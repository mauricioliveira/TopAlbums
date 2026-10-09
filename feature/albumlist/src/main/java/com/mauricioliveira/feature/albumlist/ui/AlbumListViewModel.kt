package com.mauricioliveira.feature.albumlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauricioliveira.core.domain.AlbumRepository
import com.mauricioliveira.core.ui.toErrorMessageRes
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val repository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
): ViewModel() {

    private val _uiState = MutableStateFlow<AlbumListUiState>(AlbumListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadAlbums()
    }

    fun retry() {
        _uiState.update { AlbumListUiState.Loading }
        loadAlbums()
    }

    fun refresh() {
        val current = _uiState.value
        if (current !is AlbumListUiState.Success) return retry()
        _uiState.update { AlbumListUiState.Loading }
        loadAlbums()
    }

    fun onRefreshErrorShown() {
        _uiState.update { state ->
            if (state is AlbumListUiState.Success) {
                state.copy(refreshErrorRes = null)
            } else {
                state
            }
        }
    }

    private fun loadAlbums() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch(dispatcher) {
            try {
                _uiState.update {
                    AlbumListUiState.Success(albums = repository.getTopAlbums())
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val messageRes = e.toErrorMessageRes()
                _uiState.update { state ->
                    if (state is AlbumListUiState.Success) {
                        state.copy(isRefreshing = false, refreshErrorRes = messageRes)
                    } else {
                        AlbumListUiState.Error(messageRes)
                    }
                }
            }
        }
    }
}