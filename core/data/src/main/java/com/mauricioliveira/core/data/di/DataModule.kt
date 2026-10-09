package com.mauricioliveira.core.data.di

import com.mauricioliveira.core.data.BuildConfig
import com.mauricioliveira.core.data.DefaultAlbumRepository
import com.mauricioliveira.core.data.remote.ITunesApi
import com.mauricioliveira.core.data.remote.createHttpClient
import com.mauricioliveira.core.domain.AlbumRepository
import org.koin.dsl.module

val dataModule = module {
    single { createHttpClient(enableLogging = BuildConfig.DEBUG) }
    single { ITunesApi(client = get()) }
    single<AlbumRepository> { DefaultAlbumRepository(api = get()) }
}