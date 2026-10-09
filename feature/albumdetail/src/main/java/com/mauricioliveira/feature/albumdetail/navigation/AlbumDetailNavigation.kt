package com.mauricioliveira.feature.albumdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDetailDestination(val albumId: String)

fun NavController.navigateToAlbumDetail(albumId: String) {
    navigate(AlbumDetailDestination(albumId))
}

fun NavGraphBuilder.albumDetailScreen(onBack: () -> Unit) {
    composable<AlbumDetailDestination> { entry ->
        AlbumDetailRoute(
            albumId = entry.toRoute<AlbumDetailDestination>().albumId,
            onBack = onBack
        )
    }
}