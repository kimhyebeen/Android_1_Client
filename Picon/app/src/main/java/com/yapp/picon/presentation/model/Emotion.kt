package com.yapp.picon.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Emotion(val rgb: String) : Parcelable {
    SOFT_BLUE("#8187DA"),
    CORN_FLOWER("#6699FC"),
    BLUE_GRAY("#79AED0"),
    VERY_LIGHT_BROWN("#E6AF75"),
    WARM_GRAY("#9A948B")
}