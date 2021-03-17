package com.movies.android.data.api

import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("/3/discover/movie")
    suspend fun getMovies() : Response<PopularMoviesResponse>
}