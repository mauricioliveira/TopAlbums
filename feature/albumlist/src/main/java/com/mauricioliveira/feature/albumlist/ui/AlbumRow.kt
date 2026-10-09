package com.mauricioliveira.feature.albumlist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mauricioliveira.core.domain.Album
import com.mauricioliveira.core.ui.TopAlbumsTheme

@Composable
fun AlbumRow(
    modifier: Modifier = Modifier,
    album: Album,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        leadingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = album.rank.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.width(32.dp),
                )
                AsyncImage(
                    model = album.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                    error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)),
                )
            }
        },
        headlineContent = { Text(album.name, maxLines = 2, overflow = TextOverflow.Ellipsis) },
        supportingContent = { Text(album.artistName, maxLines = 1, overflow = TextOverflow.Ellipsis) },
    )
}

@Preview(showBackground = true)
@Composable
private fun AlbumRowPreview() {
    TopAlbumsTheme {
        Surface {
            AlbumRow(
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
                ),
                onClick = {}
            )
        }
    }
}
