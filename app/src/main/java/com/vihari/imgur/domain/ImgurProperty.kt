package com.vihari.imgur.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Kotlin data classes that represents the objects in app
 * */

@Parcelize
data class ImgurProperty(val title: String?, val link: String?) : Parcelable