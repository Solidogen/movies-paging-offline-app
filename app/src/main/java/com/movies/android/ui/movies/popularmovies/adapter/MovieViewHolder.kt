package com.movies.android.ui.movies.popularmovies.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.movies.android.data.domain.MovieDomain
import com.movies.android.databinding.ItemPopularMovieBinding

class MovieViewHolder(
    private val binding: ItemPopularMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieDomain?, onMovieClicked: (MovieDomain) -> Unit) {
        binding.movieTitleTextView.text = item?.title.orEmpty()
        binding.movieImageView.load(item?.imageUrl)
        binding.root.setOnClickListener {
            item?.let {
                onMovieClicked.invoke(it)
            }
        }
    }
}