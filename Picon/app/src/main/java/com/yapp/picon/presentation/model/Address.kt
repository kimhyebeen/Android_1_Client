package com.yapp.picon.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val address: String,
    val addrCity: String,
    val addrDo: String,
    val addrGu: String
) : Parcelable