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
class SearchAdapter(val onClickListener: OnClickListener) : ListAdapter<ImgurProperty, SearchAdapter.ImgurPropertyViewHolder>(DiffCallback) {


    class ImgurPropertyViewHolder(var binding: SearchItemViewBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): ImgurPropertyViewHolder {
        return ImgurPropertyViewHolder(SearchItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: ImgurPropertyViewHolder, position: Int) {
        val imgurProperty:ImgurProperty = getItem(position)
        holder.bind(imgurProperty)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(imgurProperty)
        }

    }

    class OnClickListener(val clickListener:(imgurProperty: ImgurProperty) -> Unit) {
        fun onClick(imgurProperty: ImgurProperty) = clickListener(imgurProperty)
    }

}