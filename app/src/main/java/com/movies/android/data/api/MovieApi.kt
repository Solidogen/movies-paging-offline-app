package com.movies.android.data.api

import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("http://jsonplaceholder.typicode.com/todos/1")
    suspend fun getMovies() : Response<List<MovieDto>>
}