package com.movies.android.ui.movies.popularmovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.movies.android.data.domain.MovieDomain
import com.movies.android.databinding.ItemPopularMovieBinding

class PopularMoviesPagingAdapter(
    private val onMovieClicked: (MovieDomain) -> Unit
) : PagingDataAdapter<MovieDomain, MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(item = getItem(position), onMovieClicked = onMovieClicked)
    }
}