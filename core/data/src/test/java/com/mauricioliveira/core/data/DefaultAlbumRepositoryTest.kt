package com.mauricioliveira.core.data

import com.mauricioliveira.core.data.remote.ITunesApi
import com.mauricioliveira.core.data.remote.createHttpClient
import com.mauricioliveira.core.data.utils.TEST_FEED_JSON
import com.mauricioliveira.core.domain.AppError
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertFailsWith

class DefaultAlbumRepositoryTest {

    private fun repositoryWith(engine: MockEngine) =
        DefaultAlbumRepository(ITunesApi(createHttpClient(engine, enableLogging = false)))

    private fun MockRequestHandleScope.respondFixture(): HttpResponseData {
        val respond = respond(
            content = TEST_FEED_JSON,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "text/javascript; charset=UTF-8"),
        )
        return respond
    }

    @Test
    fun `getTopAlbums calls the top 100 endpoint and maps albums`() = runTest {
        val engine = MockEngine { respondFixture() }

        val albums = repositoryWith(engine).getTopAlbums()

        assertEquals("/us/rss/topalbums/limit=100/json", engine.requestHistory.single().url.encodedPath)
        assertEquals(1, albums.first().rank)
    }

    @Test
    fun `getAlbum reuses cached albums without a new request`() = runTest {
        val engine = MockEngine { respondFixture() }
        val repository = repositoryWith(engine)
        val first = repository.getTopAlbums().first()

        assertEquals(first, repository.getAlbum(first.id))
        assertEquals(1, engine.requestHistory.size)
    }

    @Test
    fun `network failure maps to NoConnection`() = runTest {
        val engine = MockEngine { throw kotlinx.io.IOException("offline") }

        assertFailsWith<AppError.NoConnection> { repositoryWith(engine).getTopAlbums() }
    }

    @Test
    fun `server error maps to Server with status code`() = runTest {
        val engine = MockEngine { respond("", HttpStatusCode.ServiceUnavailable) }

        val error = assertFailsWith<AppError.Server> { repositoryWith(engine).getTopAlbums() }
        assertEquals(503, error.statusCode)
    }

    @Test
    fun `unknown album id throws NotFound`() = runTest {
        val engine = MockEngine { respondFixture() }

        assertFailsWith<AppError.NotFound> { repositoryWith(engine).getAlbum("missing") }
    }
}
