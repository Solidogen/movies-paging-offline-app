package com.movies.android.data.api

import com.squareup.moshi.Json

data class PopularMoviesResponse(
    @Json(name = "page")
    val page: Int?,
    @Json(name = "results")
    val movies: List<MovieDto>?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "total_results")
    val totalResults: Int?
)