package com.movies.android.data.api

import com.movies.android.data.domain.LikedProfile
import kotlinx.coroutines.delay

class LikedProfilesApi {
    @OptIn(ExperimentalStdlibApi::class)
    suspend fun getLikedProfiles(page: Int, perPage: Int): List<LikedProfile> {
        delay(100)
        return buildList { repeat(perPage) { add(LikedProfile.createRandom()) } }
    }
}