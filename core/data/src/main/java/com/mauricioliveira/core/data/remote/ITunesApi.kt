package com.mauricioliveira.core.data.remote

import com.mauricioliveira.core.data.remote.dto.AlbumResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class ITunesApi(private val client: HttpClient) {
    suspend fun getTopAlbums(limit: Int): AlbumResponse =
        client.get("us/rss/topalbums/limit=$limit/json").body()
}