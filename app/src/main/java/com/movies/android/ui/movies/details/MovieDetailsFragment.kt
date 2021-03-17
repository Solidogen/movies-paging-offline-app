package com.movies.android.ui.movies.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.movies.android.R
import com.movies.android.databinding.FragmentMovieDetailsBinding
import com.movies.android.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)
    private val viewModel: MovieDetailsViewModel by viewModel()
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        loadMovie(movieId)
    }

    private fun loadMovie(movieId: Int) {
        lifecycleScope.launchWhenCreated {
            val movie = viewModel.getMovieById(movieId = movieId)
            binding.titleTextView.text = movie.title
            binding.posterImageView.load(movie.imageUrl)
            binding.overviewTextView.text = movie.overview
            binding.releaseDateTextView.text = movie.releaseDate
        }
    }
}