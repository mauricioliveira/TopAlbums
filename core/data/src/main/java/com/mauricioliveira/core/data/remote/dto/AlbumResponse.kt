package com.mauricioliveira.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class AlbumResponse(val feed: Feed) {
    @Serializable
    data class Feed(val entry: List<AlbumDto> = emptyList())
}