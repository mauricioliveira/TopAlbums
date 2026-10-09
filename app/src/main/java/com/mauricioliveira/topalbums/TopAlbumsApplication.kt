package com.mauricioliveira.topalbums

import android.app.Application
import com.mauricioliveira.core.data.di.dataModule
import com.mauricioliveira.feature.albumdetail.di.albumDetailModule
import com.mauricioliveira.feature.albumlist.di.albumListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TopAlbumsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TopAlbumsApplication)
            modules(dataModule, albumListModule, albumDetailModule)
        }
    }
}