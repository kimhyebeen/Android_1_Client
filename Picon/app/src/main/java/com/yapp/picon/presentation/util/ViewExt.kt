package com.yapp.picon.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrInvisible")
fun View.visibleOrInvisible(value: Boolean) {
    this.visibility = when (value) {
        true -> View.VISIBLE
        false -> View.INVISIBLE
    }
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(value: Boolean) {
    this.visibility = when (value) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}

@BindingAdapter("visibleOrGoneStr")
fun View.visibleOrGoneStr(value: String) {
    this.visibility = when (value) {
        "true" -> View.VISIBLE
        "TRUE" -> View.VISIBLE
        "false" -> View.GONE
        "FALSE" -> View.GONE
        else -> this.visibility
    }
  
@BindingAdapter("imgLoad")
fun ImageView.imgLoad(value: Int) {
    this.setImageResource(value)
}