package com.example.marvelcharacters.retrofit
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MarvelResponse(
    val code: Int,
    val status: String,
    val data: MarvelData
)

data class MarvelData(
    val results: List<MarvelCharacter>
)

@Parcelize
data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: MarvelThumbnail,
    val comics: MarvelResourceList,
    val series: MarvelResourceList,
    val stories: MarvelResourceList,
    val events: MarvelResourceList,
    val urls: List<MarvelUrl>
) : Parcelable

@Parcelize
data class MarvelThumbnail(
    val path: String,
    val extension: String
) : Parcelable

@Parcelize
data class MarvelResourceList(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelResourceItem>,
    val returned: Int
) : Parcelable

@Parcelize
data class MarvelResourceItem(
    val resourceURI: String,
    val name: String
) : Parcelable

@Parcelize
data class MarvelUrl(
    val type: String,
    val url: String
) : Parcelable
