package com.mauricioliveira.feature.albumlist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mauricioliveira.feature.albumlist.ui.AlbumListScreen
import com.mauricioliveira.feature.albumlist.ui.AlbumListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AlbumListRoute(
    onAlbumClick: (String) -> Unit,
    viewModel: AlbumListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AlbumListScreen(
        uiState = uiState,
        onAlbumClick = onAlbumClick,
        onRetry = viewModel::retry,
        onRefresh = viewModel::refresh,
        onRefreshErrorShown = viewModel::onRefreshErrorShown
    )
}