package com.mauricioliveira.core.domain

sealed class AppError(cause: Throwable? = null): Exception(cause) {
    class NoConnection(cause: Throwable): AppError(cause)
    class Server(val statusCode: Int, cause: Throwable): AppError(cause)
    class NotFound(val albumId: String): AppError()
    class Unknow(cause: Throwable): AppError(cause)
}