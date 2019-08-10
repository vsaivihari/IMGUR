package com.vihari.imgur.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vihari.imgur.domain.ImgurProperty
import com.vihari.imgur.network.ImgurApi
import kotlinx.coroutines.*

/**
 *ViewModel attached to SearchActivity
 */
class SearchViewModel : ViewModel() {


    private val _properties = MutableLiveData<List<ImgurProperty>>()
    val properties: LiveData<List<ImgurProperty>>
        get() = _properties

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val domainImgurList = ArrayList<ImgurProperty>()


    init {
        getImgurProperties("vanilla")
    }

    fun getImgurProperties(givenValue: String) {
        coroutineScope.launch {
            try {
                getImgurPropertiesFromService(givenValue)
                _properties.value = domainImgurList
            } catch (e: Exception) {

            }
        }
    }

    suspend fun getImgurPropertiesFromService(givenValue: String) {
        withContext(Dispatchers.IO) {
            val listResult = ImgurApi.retrofitService.getProperties(givenValue).await()

            try {
                for (i in listResult.data.indices) {
                    when {
                        listResult.data[i].images?.get(0)?.link.equals(null) -> {}
                        listResult.data[i].images?.get(0)?.link!!.contains(".mp4") -> {}
                        else -> {
                            domainImgurList.add(ImgurProperty(listResult.data[i].title, listResult.data[i].images?.get(0)?.link))
                        }
                    }
                }
            } catch (e: Exception) {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}