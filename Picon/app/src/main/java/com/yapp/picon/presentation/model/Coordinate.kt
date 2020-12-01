package com.yapp.picon.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Coordinate(
    val lat: BigDecimal,
    val lng: BigDecimal
) : Parcelable