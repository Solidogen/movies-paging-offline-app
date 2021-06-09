package com.movies.android.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.android.data.domain.LikedProfile
import com.movies.android.databinding.ItemLikedProfileBinding

class LikedProfilesPagingAdapter : PagingDataAdapter<LikedProfile, LikedProfilesPagingAdapter.LikedProfileViewHolder>(
    LikedProfileDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedProfileViewHolder {
        val binding =
            ItemLikedProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikedProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedProfileViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }

    class LikedProfileViewHolder(
        private val binding: ItemLikedProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LikedProfile?) {
            binding.profileNameView.text = item?.username.orEmpty()
        }
    }

    class LikedProfileDiffCallback : DiffUtil.ItemCallback<LikedProfile>() {

        override fun areItemsTheSame(oldItem: LikedProfile, newItem: LikedProfile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LikedProfile, newItem: LikedProfile): Boolean {
            return oldItem == newItem
        }
    }
}