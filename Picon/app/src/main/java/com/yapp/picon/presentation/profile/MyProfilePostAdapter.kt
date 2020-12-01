package com.yapp.picon.presentation.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yapp.picon.R
import com.yapp.picon.databinding.MyProfilePostItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.Emotion
import com.yapp.picon.presentation.model.Post
import kotlinx.android.synthetic.main.my_profile_post_item.view.*

class MyProfilePostAdapter(
    private val context: Context,
    private val clickEvent: (View, Post) -> Unit,
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<Post, MyProfilePostItemBinding>(
    layoutRes,
    bindingVariabledId
) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<MyProfilePostItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)
        // todo - 게시물 아이템 사이즈 조절하기
        baseViewHolder.itemView.apply {
            items[position].emotion?.let { setBackgroundResource(getColors(it)) }
            setOnClickListener { clickEvent(it, items[position]) }
        }

        val multiOption = MultiTransformation(
            CenterCrop(),
            RoundedCorners(5)
        )
        items[position].imageUrls?.let { imageList ->
            Glide.with(context)
                .load(imageList[0])
                .apply(RequestOptions.bitmapTransform(multiOption))
                .into(baseViewHolder.itemView.my_profile_post_item_image)
        }
    }

//    private fun dpToPx(context: Context, dp: Float): Float {
//        val displayMetrics = context.resources.displayMetrics
//
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
//    }

    private fun getColors(color: Emotion): Int {
        return when(color) {
            Emotion.SOFT_BLUE -> R.drawable.ic_custom_rounded_soft_blue
            Emotion.CORN_FLOWER -> R.drawable.ic_custom_rounded_corn_flower
            Emotion.BLUE_GRAY -> R.drawable.ic_custom_rounded_blue_gray
            Emotion.VERY_LIGHT_BROWN -> R.drawable.ic_custom_rounded_very_light_brown
            Emotion.WARM_GRAY -> R.drawable.ic_custom_rounded_warm_gray
        }
    }
}