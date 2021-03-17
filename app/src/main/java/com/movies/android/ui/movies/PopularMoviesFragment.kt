package com.movies.android.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.movies.android.R
import com.movies.android.databinding.FragmentPopularMoviesBinding
import com.movies.android.util.viewBinding

class PopularMoviesFragment : Fragment() {

    private val binding by viewBinding(FragmentPopularMoviesBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testTextView.setOnClickListener {
            findNavController().navigate(R.id.action_popular_movies_to_movie_details)
        }
    }
}