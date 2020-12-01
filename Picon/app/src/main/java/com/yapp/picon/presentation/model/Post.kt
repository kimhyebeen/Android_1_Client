package com.yapp.picon.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Post(
    val id: Int?,
    val coordinate: Coordinate,
    val imageUrls: List<String>?,
    val address: Address,
    val emotion: Emotion?,
    val memo: String?
) : Parcelable