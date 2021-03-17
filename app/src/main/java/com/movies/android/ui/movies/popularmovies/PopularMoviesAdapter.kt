package com.movies.android.ui.movies.popularmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.movies.android.data.domain.MovieDomain
import com.movies.android.databinding.ItemPopularMovieBinding

class PopularMoviesAdapter(
    private val onMovieClicked: (MovieDomain) -> Unit
) : ListAdapter<MovieDomain, PopularMoviesAdapter.MovieViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemPopularMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(item = getItem(position), onMovieClicked = onMovieClicked)
    }

    class MovieViewHolder(private val binding: ItemPopularMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieDomain, onMovieClicked: (MovieDomain) -> Unit) {
            binding.movieTitleTextView.text = item.title
            binding.movieImageView.load(item.imageUrl)
            binding.root.setOnClickListener {
                onMovieClicked.invoke(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MovieDomain>() {

        override fun areItemsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
            return oldItem == newItem
        }
    }
}