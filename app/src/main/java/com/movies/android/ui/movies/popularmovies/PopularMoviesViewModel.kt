package com.movies.android.ui.movies.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movies.android.data.domain.MovieDomain
import com.movies.android.data.repository.MovieRepository
import com.movies.android.util.MovieDisplayMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class PopularMoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val mutableSearchQueryFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val mutableSearchResultsFlow: MutableStateFlow<List<MovieDomain>> =
        MutableStateFlow(emptyList())

    val popularMoviesFlow: Flow<PagingData<MovieDomain>> =
        movieRepository.popularMoviesFlow.cachedIn(viewModelScope)

    val searchResultsFlow: Flow<List<MovieDomain>>
        get() = mutableSearchResultsFlow

    val movieDisplayModeFlow: Flow<MovieDisplayMode>
        get() = mutableSearchQueryFlow.map { searchQuery ->
            when {
                searchQuery.isNotBlank() -> MovieDisplayMode.Search
                else -> MovieDisplayMode.Popular
            }
        }

    fun setSearchQuery(searchQuery: String) {
        viewModelScope.launch {
            mutableSearchQueryFlow.emit(searchQuery)
            if (searchQuery.isNotBlank()) {
                performSearch(searchQuery)
            }
        }
    }

    private suspend fun performSearch(searchQuery: String) {
        movieRepository.searchForMovieTitle(title = searchQuery)
            .onSuccess {
                viewModelScope.launch {
                    mutableSearchResultsFlow.emit(it)
                }
            }
            .onError {
                Timber.e("Search results error: $it")
                // todo show search error
            }
    }

    fun deleteAllPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) { movieRepository.deleteAllPopularMovies() }
    }

    fun changeNamesOfAllPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) { movieRepository.changeNamesOfAllPopularMovies() }
    }
}