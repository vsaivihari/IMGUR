package com.vihari.imgur.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vihari.imgur.domain.ImgurProperty

class DetailImageViewModel(app: Application): AndroidViewModel(app) {

    val selectedProperty = MutableLiveData<ImgurProperty>()

}