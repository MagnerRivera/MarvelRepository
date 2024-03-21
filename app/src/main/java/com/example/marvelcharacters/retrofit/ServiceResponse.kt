package com.example.marvelcharacters.retrofit

data class MarvelResponse(
    val code: Int,
    val status: String,
    val data: MarvelData
)

data class MarvelData(
    val results: List<MarvelCharacter>
)

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
)

data class MarvelThumbnail(
    val path: String,
    val extension: String
)

data class MarvelResourceList(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelResourceItem>,
    val returned: Int
)

data class MarvelResourceItem(
    val resourceURI: String,
    val name: String
)

data class MarvelUrl(
    val type: String,
    val url: String
)
