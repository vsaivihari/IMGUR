package com.vihari.imgur.ui


import android.view.View
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

    var hintTextVisibility: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(View.VISIBLE) }
    var hintText: MutableLiveData<String> = MutableLiveData<String>().apply { postValue("Search for Imgur Images using Search Icon in App Bar") }


        // Update the UI in Main thread using coroutine
    fun getImgurProperties(givenValue: String) {
        coroutineScope.launch {
            try {
                getImgurPropertiesFromService(givenValue)

                when (domainImgurList.size == 0) {
                    true -> {
                        hintTextVisibility.value = View.VISIBLE
                        hintText.value = "No Images Found, Please give another try. Go easy this time!!"
                    }
                    false -> {
                        hintTextVisibility.value = View.GONE
                        _properties.value = domainImgurList
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    /**
     * @ Load data from Rest Service
     */
    suspend fun getImgurPropertiesFromService(givenValue: String) {
        withContext(Dispatchers.IO) {
            val listResult = ImgurApi.retrofitService.getProperties(givenValue).await()
            domainImgurList.clear()
            try {
                for (i in listResult.data.indices) {
                    when {
                        listResult.data[i].images?.get(0)?.link.equals(null) -> { }
                        listResult.data[i].images?.get(0)?.link!!.contains(".mp4") -> { }
                        else -> {
                            domainImgurList.add(
                                ImgurProperty(
                                    listResult.data[i].title,
                                    listResult.data[i].images?.get(0)?.link))
                        }
                    }
                }
            } catch (e: Exception) {
                hintTextVisibility.value = View.VISIBLE
                hintText.value = "Network Error!"
            }
        }
    }


    // ViewModel job is canceled
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}