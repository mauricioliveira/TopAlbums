package com.mauricioliveira.feature.albumlist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mauricioliveira.core.domain.Album
import com.mauricioliveira.core.ui.ErrorView
import com.mauricioliveira.core.ui.LoadingView
import com.mauricioliveira.core.ui.TopAlbumsTheme
import com.mauricioliveira.feature.albumlist.R
import com.mauricioliveira.core.ui.R as CoreUiR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListScreen(
    modifier: Modifier = Modifier,
    uiState: AlbumListUiState,
    onAlbumClick: (String) -> Unit,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    onRefreshErrorShown: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val refreshError = (uiState as? AlbumListUiState.Success)?.refreshErrorRes?.let { stringResource(it) }
    LaunchedEffect(refreshError) {
        if (refreshError != null) {
            snackbarHostState.showSnackbar(refreshError)
            onRefreshErrorShown()
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(R.string.album_list_title)) },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        val contentModifier = Modifier.padding(padding)
        when (uiState) {
            AlbumListUiState.Loading -> LoadingView(contentModifier)
            is AlbumListUiState.Error -> ErrorView(
                modifier = contentModifier,
                messageRes = uiState.messageRes,
                onRetry = onRetry,
            )
            is AlbumListUiState.Success -> PullToRefreshBox(
                modifier = contentModifier,
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.albums, key = { it.id }) { album ->
                        AlbumRow(album = album, onClick = { onAlbumClick(album.id) })
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AlbumListScreenSuccessPreview() {
    TopAlbumsTheme {
        AlbumListScreen(
            uiState = AlbumListUiState.Success(
                albums = listOf(
                    Album(
                        id = "1",
                        rank = 1,
                        name = "The Dark Side of the Moon",
                        artistName = "Pink Floyd",
                        imageUrl = "",
                        genre = "Rock",
                        releaseDate = "1973-03-01",
                        trackCount = 10,
                        price = "$9.99",
                        copyright = "℗ 1973 Pink Floyd Music Ltd.",
                        storeUrl = "https://apple.com"
                    ),
                    Album(
                        id = "2",
                        rank = 2,
                        name = "Abbey Road",
                        artistName = "The Beatles",
                        imageUrl = "",
                        genre = "Rock",
                        releaseDate = "1969-09-26",
                        trackCount = 17,
                        price = "$9.99",
                        copyright = "℗ 1969 Apple Records",
                        storeUrl = "https://apple.com"
                    ),
                    Album(
                        id = "3",
                        rank = 3,
                        name = "Thriller",
                        artistName = "Michael Jackson",
                        imageUrl = "",
                        genre = "Pop",
                        releaseDate = "1982-11-30",
                        trackCount = 9,
                        price = "$9.99",
                        copyright = "℗ 1982 MJJ Productions Inc.",
                        storeUrl = "https://apple.com"
                    )
                )
            ),
            onAlbumClick = {},
            onRetry = {},
            onRefresh = {},
            onRefreshErrorShown = {}
        )
    }
}