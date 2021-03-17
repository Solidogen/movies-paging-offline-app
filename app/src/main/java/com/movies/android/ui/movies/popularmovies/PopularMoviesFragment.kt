package com.movies.android.ui.movies.popularmovies

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.android.R
import com.movies.android.databinding.FragmentPopularMoviesBinding
import com.movies.android.util.viewBinding
import org.koin.core.component.KoinComponent
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies), KoinComponent {

    private val binding by viewBinding(FragmentPopularMoviesBinding::bind)
    private val viewModel: PopularMoviesViewModel by viewModel()
    private val moviesAdapter = PopularMoviesAdapter(onMovieClicked = { movie ->
        goToMovieDetails(movie.id)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupView()
    }

    private fun observeViewModel() {
        viewModel.filteredMoviesLiveData.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
    }

    private fun setupView() {
        binding.popularMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
        binding.filterEditText.addTextChangedListener { editable ->
            editable?.toString()?.let { filterText ->
                viewModel.setFilter(filterText = filterText)
            }
        }
    }

    // todo - this should be testable from click to navigate action, so navigation event livedata
    private fun goToMovieDetails(movieId: Int) {
        findNavController().navigate(
            PopularMoviesFragmentDirections.actionPopularMoviesToMovieDetails(movieId)
        )
    }
}