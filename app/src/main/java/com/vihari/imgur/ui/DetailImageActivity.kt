package com.vihari.imgur.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.vihari.imgur.R
import com.vihari.imgur.databinding.ActivityDetailBinding
import com.vihari.imgur.domain.ImgurProperty


class DetailImageActivity : AppCompatActivity() {
    val EXTRA_MESSAGE = "com.vihari.imgur.ui.DetailImageActivity"

    private val viewModel: DetailImageViewModel by lazy {
        ViewModelProviders.of(this).get(DetailImageViewModel::class.java)
    }
    /**
     * Lazily initialize our ViewModel
     */
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(binding.myChildToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewModel.selectedProperty.value = (intent.getParcelableExtra(EXTRA_MESSAGE))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true}
        }
        return super.onOptionsItemSelected(item)

    }

}
