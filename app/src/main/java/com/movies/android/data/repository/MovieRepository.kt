package com.movies.android.data.repository

import androidx.paging.*
import com.movies.android.data.api.MovieApi
import com.movies.android.data.database.AppDatabase
import com.movies.android.data.domain.MovieDomain
import com.movies.android.data.mapper.mapToDomain
import com.movies.android.data.paging.PopularMoviesRemoteMediator
import com.movies.android.util.ApiResult
import com.movies.android.util.bodyOrThrow
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class MovieRepository(private val appDatabase: AppDatabase, private val movieApi: MovieApi) {

    val popularMoviesFlow: Flow<PagingData<MovieDomain>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = PopularMoviesRemoteMediator(appDatabase, movieApi)
        ) {
            appDatabase.movieDao().getMoviesPagingSource()
        }.flow.map { it.map { entity -> entity.mapToDomain() } }

    suspend fun getMovieById(movieId: Int): MovieDomain? =
        appDatabase.movieDao().getMovieById(movieId = movieId)?.mapToDomain()

    suspend fun searchForMovieTitle(title: String): ApiResult<List<MovieDomain>> = try {
        ApiResult.Success(
            movieApi.searchForMovieTitle(title = title)
                .bodyOrThrow()
                .movies
                .orEmpty()
                .map { it.mapToDomain() }
        )
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        ApiResult.Error(e)
    }

    suspend fun deleteAllPopularMovies() {
        appDatabase.movieDao().deleteAllPopularMovies()
    }

    suspend fun changeNamesOfAllPopularMovies() {
        val movies = appDatabase.movieDao().getMovies()
        appDatabase.movieDao().updateMovies(movies.map { it.copy(title = it.title + " (up)") })
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}