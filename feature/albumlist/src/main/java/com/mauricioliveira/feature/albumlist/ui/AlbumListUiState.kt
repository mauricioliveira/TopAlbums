package com.mauricioliveira.feature.albumlist.ui

import androidx.annotation.StringRes
import com.mauricioliveira.core.domain.Album

sealed interface AlbumListUiState {
    data object Loading: AlbumListUiState

    data class Success(
        val albums: List<Album>,
        val isRefreshing: Boolean = false,
        @StringRes val refreshErrorRes: Int? = null,
    ): AlbumListUiState

    data class Error(@StringRes val messageRes: Int): AlbumListUiState
}