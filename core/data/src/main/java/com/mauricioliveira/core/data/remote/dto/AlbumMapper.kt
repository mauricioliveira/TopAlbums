package com.mauricioliveira.core.data.remote.dto

import com.mauricioliveira.core.domain.Album

internal fun AlbumResponse.toDomain(): List<Album> =
    feed.entry.mapIndexed { index, album -> album.toDomain(rank = index + 1) }

internal fun AlbumDto.toDomain(rank: Int): Album = Album(
    id = id.attributes.id,
    rank = rank,
    name = name.label,
    artistName = artist.label,
    imageUrl = images.maxByOrNull { it.attributes.height.toIntOrNull() ?: 0 }?.label.orEmpty(),
    genre = category?.attributes?.label,
    releaseDate = releaseDate?.attributes?.label ?: releaseDate?.label,
    trackCount = itemCount?.label?.toIntOrNull(),
    price = price?.label,
    copyright = rights?.label,
    storeUrl = link?.attributes?.href,
)