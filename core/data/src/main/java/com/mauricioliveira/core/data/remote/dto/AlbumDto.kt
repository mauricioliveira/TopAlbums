package com.mauricioliveira.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AlbumDto(
    val id: Id,
    @SerialName("im:name") val name: Label,
    @SerialName("im:artist") val artist: Label,
    @SerialName("im:image") val images: List<Image> = emptyList(),
    @SerialName("im:itemCount") val itemCount: Label? = null,
    @SerialName("im:price") val price: Label? = null,
    @SerialName("im:releaseDate") val releaseDate: ReleaseDate? = null,
    val category: Category? = null,
    val rights: Label? = null,
    val link: Link? = null,
) {
    @Serializable
    data class Label(val label: String)

    @Serializable
    data class Image(val label: String, val attributes: Attributes) {
        @Serializable
        data class Attributes(val height: String)
    }

    @Serializable
    data class ReleaseDate(val label: String, val attributes: Label? = null)

    @Serializable
    data class Category(val attributes: Label)

    @Serializable
    data class Id(val attributes: Attributes) {
        @Serializable
        data class Attributes(@SerialName("im:id") val id: String)
    }

    @Serializable
    data class Link(val attributes: Attributes) {
        @Serializable
        data class Attributes(val href: String)
    }
}