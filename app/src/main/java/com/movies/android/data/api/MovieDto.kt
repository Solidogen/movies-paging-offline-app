package com.movies.android.data.api

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "imageUrl")
    val imageUrl: String,
)
