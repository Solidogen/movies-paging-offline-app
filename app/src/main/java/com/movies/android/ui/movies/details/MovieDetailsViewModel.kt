package com.movies.android.ui.movies.details

import androidx.lifecycle.ViewModel
import com.movies.android.data.repository.MovieRepository

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    suspend fun getMovieById(movieId: Int) = movieRepository.getMovieById(movieId = movieId)
}