package com.mauricioliveira.topalbums

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mauricioliveira.core.ui.TopAlbumsTheme
import com.mauricioliveira.topalbums.navigation.TopAlbumsNavHost

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TopAlbumsTheme {
                TopAlbumsNavHost()
            }
        }
    }
}