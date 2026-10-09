package com.mauricioliveira.feature.albumdetail.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mauricioliveira.feature.albumdetail.ui.AlbumDetailScreen
import com.mauricioliveira.feature.albumdetail.ui.AlbumDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AlbumDetailRoute(
    albumId: String,
    onBack: () -> Unit,
    viewModel: AlbumDetailViewModel = koinViewModel { parametersOf(albumId) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AlbumDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::retry
    )
}