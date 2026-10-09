package com.mauricioliveira.feature.albumdetail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mauricioliveira.core.domain.Album
import com.mauricioliveira.core.ui.ErrorView
import com.mauricioliveira.core.ui.LoadingView
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.mauricioliveira.core.ui.TopAlbumsTheme
import com.mauricioliveira.feature.albumdetail.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    uiState: AlbumDetailUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.album_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.navigate_back))
                    }
                },
            )
        },
    ) { padding ->
        val contentModifier = Modifier.padding(padding)
        when (uiState) {
            AlbumDetailUiState.Loading -> LoadingView(contentModifier)
            is AlbumDetailUiState.Error -> ErrorView(
                modifier = contentModifier,
                messageRes = uiState.messageRes,
                onRetry = onRetry
            )
            is AlbumDetailUiState.Success -> AlbumDetailContent(
                modifier = contentModifier,
                album = uiState.album
            )
        }
    }
}

@Composable
private fun AlbumDetailContent(
    modifier: Modifier = Modifier,
    album: Album
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = album.imageUrl,
            contentDescription = stringResource(R.string.album_artwork, album.name),
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
        )
        Text(
            album.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            album.artistName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                album.genre?.let {
                    DetailRow(
                        label = stringResource(R.string.detail_genre),
                        value = it
                    )
                }
                album.releaseDate?.let {
                    DetailRow(
                        label = stringResource(R.string.detail_release_date),
                        value = it
                    )
                }
                album.trackCount?.let {
                    DetailRow(
                        label = stringResource(R.string.detail_tracks),
                        value = it.toString()
                    )
                }
                album.price?.let {
                    DetailRow(
                        label = stringResource(R.string.detail_price),
                        value = it
                    )
                }
                album.copyright?.let {
                    DetailRow(
                        label = stringResource(R.string.detail_copyright),
                        value = it
                    )
                }
            }
        }
        album.storeUrl?.let { url ->
            Button(onClick = { uriHandler.openUri(url) }) {
                Text(stringResource(R.string.open_in_apple_music))
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AlbumDetailScreenSuccessPreview() {
    TopAlbumsTheme {
        AlbumDetailScreen(
            uiState = AlbumDetailUiState.Success(
                album = Album(
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
                )
            ),
            onBack = {},
            onRetry = {}
        )
    }
}