package com.mauricioliveira.core.domain

data class Album(
    val id: String,
    val rank: Int,
    val name: String,
    val artistName: String,
    val imageUrl: String,
    val genre: String?,
    val releaseDate: String?,
    val trackCount: Int?,
    val price: String?,
    val copyright: String?,
    val storeUrl: String?
)