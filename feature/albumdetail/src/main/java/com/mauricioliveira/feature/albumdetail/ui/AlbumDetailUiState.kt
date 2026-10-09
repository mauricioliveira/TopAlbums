package com.mauricioliveira.feature.albumdetail.ui

import androidx.annotation.StringRes
import com.mauricioliveira.core.domain.Album

sealed interface AlbumDetailUiState {
    data object Loading : AlbumDetailUiState
    data class Success(val album: Album) : AlbumDetailUiState
    data class Error(@StringRes val messageRes: Int) : AlbumDetailUiState
}