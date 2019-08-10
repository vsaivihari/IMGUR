package com.vihari.imgur.network

import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects responsible for parsing responses from the server
 *
 * @see domain package for
 */
@JsonClass(generateAdapter = true)
data class NetworkImgur(val data: List<Data>, val success: Boolean, val status: Int)

data class Images(val link: String?)

data class Data(val title: String?, val images: List<Images>?)

