package com.mauricioliveira.feature.albumlist.di

import com.mauricioliveira.feature.albumlist.ui.AlbumListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val albumListModule = module {
    viewModel { AlbumListViewModel(repository = get()) }
}