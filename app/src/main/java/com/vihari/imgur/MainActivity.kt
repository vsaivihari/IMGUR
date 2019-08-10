package com.vihari.imgur

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.vihari.imgur.databinding.ActivityMainBinding
import com.vihari.imgur.ui.SearchAdapter
import com.vihari.imgur.ui.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
*Main Activity of the Imgur app.
*/
class MainActivity : AppCompatActivity() {


    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        binding.imageRecyclerView.adapter = SearchAdapter()
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                viewModel.getImgurProperties(query)
            }
        }
    }
}
