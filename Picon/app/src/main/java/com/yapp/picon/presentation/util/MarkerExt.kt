package com.yapp.picon.presentation.util

import android.app.Activity
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.yapp.picon.R
import com.yapp.picon.data.model.Emotion
import com.yapp.picon.databinding.MarkerViewBinding
import com.yapp.picon.presentation.model.PostMarker
import jp.wasabeef.glide.transformations.MaskTransformation

fun pinMarker(
    latLng: LatLng,
    activity: Activity,
    imageUrl: String?,
    emotion: Emotion?
): Marker {
    return Marker(latLng).apply {
        activity.layoutInflater.inflate(R.layout.marker_view, null).run {
            DataBindingUtil.bind<MarkerViewBinding>(this)?.run {
                when (emotion) {
                    Emotion.SOFT_BLUE -> {
                        markerViewIvMarker.setBackgroundResource(
                            R.drawable.pin_soft_blue
                        )
                    }

                    Emotion.CORN_FLOWER -> {
                        markerViewIvMarker.setBackgroundResource(
                            R.drawable.pin_cornflower
                        )
                    }

                    Emotion.BLUE_GRAY -> {
                        markerViewIvMarker.setBackgroundResource(
                            R.drawable.pin_blue_grey
                        )
                    }

                    Emotion.VERY_LIGHT_BROWN -> {
                        markerViewIvMarker.setBackgroundResource(
                            R.drawable.pin_very_light_brown
                        )
                    }

                    Emotion.WARM_GRAY -> {
                        markerViewIvMarker.setBackgroundResource(
                            R.drawable.pin_warm_grey
                        )
                    }
                }

                Glide.with(activity)
                    .load(imageUrl)
                    .apply(
                        RequestOptions.bitmapTransform(
                            MaskTransformation(R.drawable.pin_image)
                        )
                    )
                    .thumbnail(0.1f)
                    .into(markerViewIvImage)
            }
            icon = OverlayImage.fromView(this)
        }
    }
}

fun clusterMarker(
    activity: Activity,
    postMarker: PostMarker,
    count: Int
): View {
    return activity.layoutInflater.inflate(R.layout.marker_view, null).apply {
        DataBindingUtil.bind<MarkerViewBinding>(this)?.run {
            markerViewTvCount.text = count.toString()
            markerViewTvCount.visibility = View.VISIBLE

            when (postMarker.emotion) {
                Emotion.SOFT_BLUE -> {
                    markerViewTvCount.setBackgroundResource(
                        R.drawable.bg_soft_blue_radius_1dp
                    )
                    markerViewIvMarker.setBackgroundResource(
                        R.drawable.pin_soft_blue
                    )
                }

                Emotion.CORN_FLOWER -> {
                    markerViewTvCount.setBackgroundResource(
                        R.drawable.bg_cornflower_radius_1dp
                    )
                    markerViewIvMarker.setBackgroundResource(
                        R.drawable.pin_cornflower
                    )
                }

                Emotion.BLUE_GRAY -> {
                    markerViewTvCount.setBackgroundResource(
                        R.drawable.bg_blue_grey_radius_1dp
                    )
                    markerViewIvMarker.setBackgroundResource(
                        R.drawable.pin_blue_grey
                    )
                }

                Emotion.VERY_LIGHT_BROWN -> {
                    markerViewTvCount.setBackgroundResource(
                        R.drawable.bg_very_light_brown_radius_1dp
                    )
                    markerViewIvMarker.setBackgroundResource(
                        R.drawable.pin_very_light_brown
                    )
                }

                Emotion.WARM_GRAY -> {
                    markerViewTvCount.setBackgroundResource(
                        R.drawable.bg_warm_grey_radius_1dp
                    )
                    markerViewIvMarker.setBackgroundResource(
                        R.drawable.pin_warm_grey
                    )
                }
            }

            Glide.with(activity)
                .load(postMarker.imageUrls?.random())
                .apply(
                    RequestOptions.bitmapTransform(
                        MaskTransformation(R.drawable.pin_image)
                    )
                )
                .thumbnail(0.1f)
                .into(markerViewIvImage)
        }
    }
}