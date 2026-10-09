package com.mauricioliveira.core.data

import com.mauricioliveira.core.data.remote.ITunesApi
import com.mauricioliveira.core.data.remote.dto.toDomain
import com.mauricioliveira.core.domain.Album
import com.mauricioliveira.core.domain.AlbumRepository
import com.mauricioliveira.core.domain.AppError
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

private const val TOP_ALBUMS_LIMIT = 100

internal class DefaultAlbumRepository(
    private val api: ITunesApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AlbumRepository {

    @Volatile
    private var albumsById: Map<String, Album> = emptyMap()

    override suspend fun getTopAlbums(): List<Album> = withContext(ioDispatcher) {
        val albums = safeCall { api.getTopAlbums(limit = TOP_ALBUMS_LIMIT).toDomain() }
        albumsById = albums.associateBy { it.id }
        albums
    }

    override suspend fun getAlbum(id: String): Album =
        albumsById[id]
            ?: getTopAlbums().firstOrNull { it.id == id }
            ?: throw AppError.NotFound(albumId = id)

    private suspend fun <T> safeCall(block: suspend () -> T): T =
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: ResponseException) {
            throw AppError.Server(statusCode = e.response.status.value, e)
        } catch (e: IOException) {
            throw AppError.NoConnection(cause = e)
        } catch (e: Exception) {
            throw AppError.Unknow(cause = e)
        }
}