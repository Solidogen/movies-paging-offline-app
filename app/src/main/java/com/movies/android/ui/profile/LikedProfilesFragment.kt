package com.movies.android.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.movies.android.R
import com.movies.android.databinding.FragmentProfilesBinding
import com.movies.android.ui.movies.popularmovies.adapter.MoviesLoadStateAdapter
import com.movies.android.util.setup
import com.movies.android.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LikedProfilesFragment : Fragment(R.layout.fragment_profiles) {

    private val likesPagingAdapter = LikedProfilesPagingAdapter()

    private val binding by viewBinding(FragmentProfilesBinding::bind)
    private val viewModel: LikedProfilesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.likedProfilesPagingFlow.collectLatest {
                likesPagingAdapter.submitData(it)
            }
        }
    }

    private fun setupView() {
        lifecycleScope.launchWhenCreated {
            likesPagingAdapter.loadStateFlow.collectLatest {
                try {
                    binding.profilesSwipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
        binding.profilesRecycler.setup(adapter = likesPagingAdapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter(retryListener = { likesPagingAdapter.retry() }),
            footer = MoviesLoadStateAdapter(retryListener = { likesPagingAdapter.retry() })
        ))
        binding.profilesSwipeRefreshLayout.setOnRefreshListener { likesPagingAdapter.refresh() }
    }
}