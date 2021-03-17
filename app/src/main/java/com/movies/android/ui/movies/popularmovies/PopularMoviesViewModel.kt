package com.movies.android.ui.movies.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.movies.android.data.domain.MovieDomain
import com.movies.android.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PopularMoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val allMoviesFlow: Flow<List<MovieDomain>>
        get() = movieRepository.moviesFlow

    private val mutableFilterFlow: MutableStateFlow<String> = MutableStateFlow("")

    val filteredMoviesLiveData: LiveData<List<MovieDomain>>
        get() = allMoviesFlow.combine(mutableFilterFlow) { movies, filter ->
            if (filter.isEmpty()) {
                movies
            } else {
                movies.filter { it.title.contains(filter) }
            }
        }.asLiveData()

    fun setFilter(filterText: String) {
        viewModelScope.launch {
            mutableFilterFlow.emit(filterText)
        }
    }

    fun goToMovieDetails(movieId: Int) {
        // todo livedata<event>
    }
}