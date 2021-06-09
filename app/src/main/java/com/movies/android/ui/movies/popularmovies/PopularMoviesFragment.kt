package com.movies.android.ui.movies.popularmovies

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.android.R
import com.movies.android.databinding.FragmentPopularMoviesBinding
import com.movies.android.ui.movies.popularmovies.adapter.MovieSearchAdapter
import com.movies.android.ui.movies.popularmovies.adapter.MoviesLoadStateAdapter
import com.movies.android.ui.movies.popularmovies.adapter.PopularMoviesPagingAdapter
import com.movies.android.util.MovieDisplayMode
import com.movies.android.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.core.component.KoinComponent
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies), KoinComponent {

    private val binding by viewBinding(FragmentPopularMoviesBinding::bind)
    private val viewModel: PopularMoviesViewModel by viewModel()
    private val pagingAdapter = PopularMoviesPagingAdapter(onMovieClicked = { movie ->
        goToMovieDetails(movie.id)
    })
    private val searchAdapter = MovieSearchAdapter(onMovieClicked = { movie ->
        goToMovieDetails(movie.id)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.popularMoviesFlow.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            pagingAdapter.loadStateFlow.collectLatest {
                binding.popularMoviesSwipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.searchResultsFlow.collectLatest {
                searchAdapter.submitList(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.movieDisplayModeFlow.collectLatest { movieDisplayMode ->
                binding.popularMoviesSwipeRefreshLayout.isVisible = movieDisplayMode == MovieDisplayMode.Popular
                binding.searchResultsRecycler.isVisible = movieDisplayMode == MovieDisplayMode.Search
            }
        }
    }

    private fun setupView() {
        binding.popularMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter(retryListener = { pagingAdapter.retry() }),
                footer = MoviesLoadStateAdapter(retryListener = { pagingAdapter.retry() })
            )
        }
        binding.searchResultsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
        binding.searchEditText.addTextChangedListener { editable ->
            editable?.toString()?.let { searchQuery ->
                viewModel.setSearchQuery(searchQuery = searchQuery)
            }
        }
        binding.popularMoviesSwipeRefreshLayout.setOnRefreshListener { pagingAdapter.refresh() }
    }

    // todo - this should be testable from click to navigate action, so navigation event flow in viewmodel
    private fun goToMovieDetails(movieId: Int) {
        findNavController().navigate(
            PopularMoviesFragmentDirections.actionPopularMoviesToMovieDetails(movieId)
        )
    }
}