package com.movies.android.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/discover/movie")
    suspend fun getPopularMovies(@Query(value = "page") page: Int?) : Response<PopularMoviesResponse>

    @GET("/3/search/movie")
    suspend fun searchForMovieTitle(@Query(value = "query") title: String) : Response<PopularMoviesResponse>
}