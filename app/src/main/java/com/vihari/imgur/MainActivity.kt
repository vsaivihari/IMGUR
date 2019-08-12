package com.vihari.imgur

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.vihari.imgur.databinding.ActivityMainBinding
import com.vihari.imgur.domain.ImgurProperty
import com.vihari.imgur.ui.DetailImageActivity

import com.vihari.imgur.ui.SearchAdapter
import com.vihari.imgur.ui.SearchViewModel


/**
*Main Activity of the Imgur app.
*/
class MainActivity : AppCompatActivity() {


    val EXTRA_MESSAGE = "com.vihari.imgur.ui.DetailImageActivity"

    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(binding.myToolbar)
        binding.imageRecyclerView.adapter = SearchAdapter(SearchAdapter.OnClickListener{goToDetailActivity(it)})

    }

    // Will send Entered Search Item to viewmodel
    override fun onResume() {
        super.onResume()
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                viewModel.getImgurProperties(query)
                viewModel.hintTextVisibility.postValue(View.GONE)
            }
        }

    }

    // Create search menu item in App Bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

        // Launch Detail Activity
        private fun goToDetailActivity(ingur: ImgurProperty) {
        val intent = Intent(this, DetailImageActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, ingur)
        startActivity(intent)
    }

}
