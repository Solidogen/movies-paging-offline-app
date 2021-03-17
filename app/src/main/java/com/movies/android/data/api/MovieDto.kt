package com.movies.android.data.api

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "overview")
    val overview: String?
)