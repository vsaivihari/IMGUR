package com.vihari.imgur.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://api.imgur.com/3/gallery/search/"

/**
 * Moshi object
 */
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

/**
 * A public interface to fetch Images.
 */
interface ImgurApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [NetworkImgur] which can be fetched with await() if
     * in a Coroutine scope.
     */
    @Headers("Authorization: Client-ID 137cda6b5008a7c")
    @GET("1?")
    fun getProperties(@Query("q") type: String): Deferred<NetworkImgur>
}

/**
 * public Api object that exposes the lazy-initialized Retrofit service
 */
object ImgurApi {
    val retrofitService: ImgurApiService by lazy {
        retrofit.create(ImgurApiService::class.java)
    }
}