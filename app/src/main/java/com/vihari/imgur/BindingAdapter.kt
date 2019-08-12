package com.vihari.imgur

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vihari.imgur.domain.ImgurProperty
import com.vihari.imgur.ui.SearchAdapter
import com.vihari.imgur.ui.SearchViewModel


// Binding Search Adapter to Recycler View
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ImgurProperty>?) {
    val adapter = recyclerView.adapter as SearchAdapter
    adapter.submitList(data)
}
/**
 * library to load an image by URL into an [ImageView]
 */

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}
