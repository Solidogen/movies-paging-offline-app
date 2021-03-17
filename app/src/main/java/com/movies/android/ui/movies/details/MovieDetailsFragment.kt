package com.movies.android.ui.movies.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.movies.android.R
import com.movies.android.databinding.FragmentMovieDetailsBinding
import com.movies.android.util.viewBinding

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding
    }
}