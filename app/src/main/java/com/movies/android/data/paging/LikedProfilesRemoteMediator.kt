package com.movies.android.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.movies.android.data.api.LikedProfilesApi
import com.movies.android.data.database.AppDatabase
import com.movies.android.data.database.entity.LikedProfileEntity
import com.movies.android.data.database.entity.RemoteKeyEntity
import com.movies.android.data.mapper.mapToEntity
import kotlinx.coroutines.CancellationException
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class LikedProfilesRemoteMediator(
    private val likedProfilesApi: LikedProfilesApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, LikedProfileEntity>() {

    private val likedProfilesDao = appDatabase.likedProfilesDao()
    private val remoteKeyDao = appDatabase.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LikedProfileEntity>
    ): MediatorResult {
        try {
            val pageBeingLoaded = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = appDatabase.withTransaction {
                        remoteKeyDao.remoteKeyByTitle(LIKED_PROFILES_REMOTE_KEY)
                    }
                    remoteKey.nextPage ?: error("No next page in remote key")
                }
            }
            Timber.d("Loading pageBeingLoaded: $pageBeingLoaded, load type: $loadType")
            val likedProfiles = likedProfilesApi.getLikedProfiles(
                page = pageBeingLoaded,
                perPage = if (loadType == LoadType.REFRESH) state.config.initialLoadSize else state.config.pageSize
            )
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    likedProfilesDao.deleteAllLikedProfiles()
                    remoteKeyDao.deleteByTitle(LIKED_PROFILES_REMOTE_KEY)
                }
                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(
                        title = LIKED_PROFILES_REMOTE_KEY,
                        nextPage = pageBeingLoaded + 1
                    )
                )
                likedProfilesDao.insert(likedProfiles.map { it.mapToEntity(apiPageIndex = pageBeingLoaded) }
                    .also {
                        Timber.d("Loaded liked profiles: ${it.count()}")
                    })
            }
            return MediatorResult.Success(endOfPaginationReached = likedProfiles.isEmpty())
        } catch (e: CancellationException) {
            Timber.e(e)
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }
    }

    companion object {
        private const val LIKED_PROFILES_REMOTE_KEY = "LIKED_PROFILES_REMOTE_KEY"
    }
}