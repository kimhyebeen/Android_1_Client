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

@BindingAdapter("onLongClick")
fun View.onLongClick(value: (View) -> Boolean) {
    this.setOnLongClickListener {
        value(it)
    }
}