package com.mauricioliveira.core.domain

interface AlbumRepository {
    suspend fun getTopAlbums(): List<Album>
    suspend fun getAlbum(id: String): Album
}