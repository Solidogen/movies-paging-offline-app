package com.movies.android.data.repository

import androidx.paging.*
import com.movies.android.data.api.LikedProfilesApi
import com.movies.android.data.database.AppDatabase
import com.movies.android.data.domain.LikedProfile
import com.movies.android.data.mapper.mapToDomain
import com.movies.android.data.paging.LikedProfilesRemoteMediator
import com.movies.android.util.BaseApiUrlProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class LikedProfilesRepository(private val appDatabase: AppDatabase, private val likedProfilesApi: LikedProfilesApi) {

    private val baseApiUrlProvider = object : BaseApiUrlProvider {}

    val likedProfilesFlow: Flow<PagingData<LikedProfile>> =
        Pager(
            config = PagingConfig(
                pageSize = LIKED_PROFILES_PER_PAGE,
                enablePlaceholders = false
            ),
            remoteMediator = LikedProfilesRemoteMediator(likedProfilesApi, appDatabase)
        ) {
            appDatabase.likedProfilesDao().getLikedProfilesPagingSource()
        }.flow.map { it.map { entity -> entity.mapToDomain(baseApiUrlProvider) } }

    companion object {
        const val LIKED_PROFILES_PER_PAGE = 20
    }
}