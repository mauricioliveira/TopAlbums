package com.mauricioliveira.core.ui

import androidx.annotation.StringRes
import com.mauricioliveira.core.domain.AppError

@StringRes
fun Throwable.toErrorMessageRes(): Int = when(this) {
    is AppError.NoConnection -> R.string.error_no_connection
    is AppError.Server -> R.string.error_server
    is AppError.NotFound -> R.string.error_album_not_found
    else -> R.string.error_generic
}