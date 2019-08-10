package com.vihari.imgur.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vihari.imgur.databinding.SearchItemViewBinding
import com.vihari.imgur.domain.ImgurProperty

/**
 * Class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class SearchAdapter : ListAdapter<ImgurProperty, SearchAdapter.ImgurPropertyViewHolder>(DiffCallback) {


    class ImgurPropertyViewHolder(private var binding: SearchItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgurProperty: ImgurProperty) {
            binding.property = imgurProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ImgurProperty>() {
        override fun areItemsTheSame(oldItem: ImgurProperty, newItem: ImgurProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImgurProperty, newItem: ImgurProperty): Boolean {
            return oldItem.link == newItem.link
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImgurPropertyViewHolder {
        return ImgurPropertyViewHolder(SearchItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: ImgurPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)
    }
}