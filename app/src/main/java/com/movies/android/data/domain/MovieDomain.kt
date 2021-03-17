package com.movies.android.data.domain

data class MovieDomain(
    val id: Int,
    val title: String,
    private val posterPath: String?,
    val overview: String,
    val releaseDate: String
) {
    val imageUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$posterPath" }
}
