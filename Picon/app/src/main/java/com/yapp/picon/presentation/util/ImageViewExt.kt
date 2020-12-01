package com.yapp.picon.presentation.util

import android.net.Uri
import android.widget.ImageView
import androidx.core.graphics.toColorInt
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("setRoundRecImg")
fun ImageView.setRoundRecImg(uri: Uri) {
    Glide.with(this)
        .load(uri)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(5)))
        .fitCenter()
        .into(this)
}

@BindingAdapter("setDrawableImg")
fun ImageView.setDrawableImg(img: String) {
    if (img.isNotEmpty()) {
        Glide.with(this)
            .load(img.toInt())
            .into(this)
    }
}

@BindingAdapter("setUrlImg")
fun ImageView.setUrlImg(url: String) {
    if (url.isNotEmpty()) {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("imgLoad")
fun ImageView.imgLoad(value: Int) {
    this.setImageResource(value)
}

@BindingAdapter("setColorStr")
fun ImageView.setColorStr(color: String) {
    if (color.isNotEmpty()) {
        this.setBackgroundColor(color.toColorInt())
    }
}