package com.yapp.picon.presentation.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrInvisible")
fun View.visibleOrInvisible(value: Boolean) {
    this.visibility = when (value) {
        true -> View.VISIBLE
        false -> View.INVISIBLE
    }
}