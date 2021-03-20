package com.movies.android.data.paging

import androidx.paging.*
import androidx.room.withTransaction
import com.movies.android.data.api.MovieApi
import com.movies.android.data.database.MovieDatabase
import com.movies.android.data.database.entity.MovieEntity
import com.movies.android.data.database.entity.RemoteKeyEntity
import com.movies.android.data.mapper.mapToEntity
import com.movies.android.util.bodyOrThrow
import kotlinx.coroutines.CancellationException
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val database: MovieDatabase,
    private val api: MovieApi
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = database.movieDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByTitle(POPULAR_MOVIES_REMOTE_KEY)
                    }
                    remoteKey.nextPage
                }
            }
            Timber.d("Loading with load key: $loadKey")
            val data = api.getPopularMovies(loadKey).bodyOrThrow()
            val movies = data.movies.orEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteAllPopularMovies()
                    remoteKeyDao.deleteByTitle(POPULAR_MOVIES_REMOTE_KEY)
                }
                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(
                        title = POPULAR_MOVIES_REMOTE_KEY,
                        nextPage = data.nextPage
                    )
                )
                movieDao.insert(movies.map { it.mapToEntity(apiPageIndex = data.page)}.also {
                    Timber.d("Loaded movies: $it")
                })
            }
            return MediatorResult.Success(endOfPaginationReached = movies.isEmpty() || data.totalPages == data.page)
        } catch (e: CancellationException) {
            Timber.e(e)
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }
    }

    companion object {
        private const val POPULAR_MOVIES_REMOTE_KEY = "POPULAR_MOVIES_REMOTE_KEY"
    }
}