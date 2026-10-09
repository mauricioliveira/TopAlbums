package com.mauricioliveira.feature.albumdetail.di

import com.mauricioliveira.feature.albumdetail.ui.AlbumDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val albumDetailModule = module {
    viewModel { params ->
        AlbumDetailViewModel(
            albumId = params.get(),
            repository = get()
        )
    }
}