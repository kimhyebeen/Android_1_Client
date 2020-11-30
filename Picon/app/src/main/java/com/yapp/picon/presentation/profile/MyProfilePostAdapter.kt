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
import com.yapp.picon.presentation.model.MyProfilePost
import kotlinx.android.synthetic.main.my_profile_post_item.view.*

class MyProfilePostAdapter(
    private val context: Context,
    private val clickEvent: (View, Int) -> Unit,
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<MyProfilePost, MyProfilePostItemBinding>(
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
        println("$position - ${items[position].color}, ${items[position].imageUrl}")

        baseViewHolder.itemView.apply {
            setBackgroundResource(
                getColors(items[position].color)
            )
            setOnClickListener { clickEvent(it, items[position].id) }
        }

        val multiOption = MultiTransformation(
            CenterCrop(),
            RoundedCorners(5)
        )
        if (items[position].imageUrl.isNotEmpty()) {
            Glide.with(context)
                .load(items[position].imageUrl)
                .apply(RequestOptions.bitmapTransform(multiOption))
                .into(baseViewHolder.itemView.my_profile_post_item_image)
        } else {
            // todo - 이미지가 없을 수도 있나??
        }
    }

//    private fun dpToPx(context: Context, dp: Float): Float {
//        val displayMetrics = context.resources.displayMetrics
//
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
//    }

    private fun getColors(color: String): Int {
        return when(color) {
            "SOFT_BLUE" -> R.drawable.ic_custom_rounded_soft_blue
            "CORN_FLOWER" -> R.drawable.ic_custom_rounded_corn_flower
            "BLUE_GRAY" -> R.drawable.ic_custom_rounded_blue_gray
            "VERY_LIGHT_BROWN" -> R.drawable.ic_custom_rounded_very_light_brown
            "WARM_GRAY" -> R.drawable.ic_custom_rounded_warm_gray
            else -> throw Exception("MyProfilePostAdapter - getColors - color type is wrong.")
        }
    }
}