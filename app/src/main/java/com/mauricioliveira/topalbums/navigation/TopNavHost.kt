package com.mauricioliveira.topalbums.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mauricioliveira.feature.albumdetail.navigation.albumDetailScreen
import com.mauricioliveira.feature.albumdetail.navigation.navigateToAlbumDetail
import com.mauricioliveira.feature.albumlist.navigation.AlbumListDestination
import com.mauricioliveira.feature.albumlist.navigation.albumListScreen

@Composable
fun TopAlbumsNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = AlbumListDestination) {
        albumListScreen(onAlbumClick = { albumId -> navController.navigateToAlbumDetail(albumId) })
        albumDetailScreen(onBack = { navController.popBackStack() })
    }
}
