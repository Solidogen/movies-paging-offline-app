package com.movies.android.data.repository

import com.movies.android.data.api.MovieApi
import com.movies.android.data.database.MovieDao
import com.movies.android.data.domain.MovieDomain
import com.movies.android.data.mapper.mapToDomain
import com.movies.android.data.mapper.mapToEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.Exception

class MovieRepository(
    nonCancellableScope: CoroutineScope,
    private val movieDao: MovieDao,
    private val movieApi: MovieApi
) {

    val moviesFlow: SharedFlow<List<MovieDomain>> = movieDao.getMoviesFlow()
        .map { it.map { it.mapToDomain() } }
        .shareIn(nonCancellableScope, SharingStarted.WhileSubscribed(0))
        .onSubscription {
            getFreshMovies()
            // todo what do I return here? Result for various cache/api errors, or just list?
        }

    private suspend fun getFreshMovies() {
        try {
            val freshMovies = movieApi.getMovies().body()!!
            movieDao.insert(freshMovies.map { it.mapToEntity() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            // todo handle this
        }
    }
}