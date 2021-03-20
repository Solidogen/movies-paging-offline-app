package com.movies.android.ui.movies.popularmovies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.movies.android.data.domain.MovieDomain

class MovieDiffCallback : DiffUtil.ItemCallback<MovieDomain>() {

    override fun areItemsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean {
        return oldItem == newItem
    }
}