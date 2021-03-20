package com.movies.android.ui.profile

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.movies.android.R
import com.movies.android.databinding.FragmentProfileBinding
import com.movies.android.util.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileRecycler.apply {
            setHasFixedSize(true)
            adapter = SimpleAdapter((1..1000).map { "Testing multiple backstacks with bottom navigation: $it" })
        }
    }

    class SimpleAdapter(private val items: List<String>) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {
        override fun onCreateViewHolder(v: ViewGroup, t: Int) = ViewHolder(TextView(v.context))
        override fun onBindViewHolder(h: ViewHolder, p: Int) { (h.itemView as TextView).text = items[p] }
        override fun getItemCount() = items.size
        class ViewHolder(view: TextView) : RecyclerView.ViewHolder(view)
    }
}