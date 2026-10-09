package com.mauricioliveira.feature.albumlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AlbumListDestination

fun NavGraphBuilder.albumListScreen(onAlbumClick: (String) -> Unit) {
    composable<AlbumListDestination> {
        AlbumListRoute(onAlbumClick = onAlbumClick)
    }
}