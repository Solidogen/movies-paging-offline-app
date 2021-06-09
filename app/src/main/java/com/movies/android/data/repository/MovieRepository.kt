package com.movies.android.data.repository

import androidx.paging.*
import com.movies.android.data.api.MovieApi
import com.movies.android.data.database.MovieDatabase
import com.movies.android.data.domain.MovieDomain
import com.movies.android.data.mapper.mapToDomain
import com.movies.android.data.paging.PageKeyedRemoteMediator
import com.movies.android.util.ApiResult
import com.movies.android.util.bodyOrThrow
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class MovieRepository(private val moviesDatabase: MovieDatabase, private val movieApi: MovieApi) {

    val popularMoviesFlow: Flow<PagingData<MovieDomain>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = PageKeyedRemoteMediator(moviesDatabase, movieApi)
        ) {
            moviesDatabase.movieDao().getMoviesFlow()
        }.flow.map { it.map { entity -> entity.mapToDomain() } }

    suspend fun getMovieById(movieId: Int): MovieDomain? =
        moviesDatabase.movieDao().getMovieById(movieId = movieId)?.mapToDomain()

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

    companion object {
        private const val PAGE_SIZE = 20
    }
}

