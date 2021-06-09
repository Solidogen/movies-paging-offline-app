package com.movies.android.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.movies.android.data.domain.LikedProfile
import com.movies.android.data.repository.LikedProfilesRepository
import kotlinx.coroutines.flow.Flow

class LikedProfilesViewModel(
    private val likedProfilesRepository: LikedProfilesRepository
) : ViewModel() {

    val likedProfilesPagingFlow: Flow<PagingData<LikedProfile>> =
        likedProfilesRepository.likedProfilesFlow.cachedIn(viewModelScope)
}
