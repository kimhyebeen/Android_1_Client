package com.yapp.picon.presentation.nav.adapter

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import com.yapp.picon.R
import com.yapp.picon.databinding.MapNavHeadEmotionItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.CustomEmotion
import com.yapp.picon.presentation.model.EmotionEntity
import kotlinx.android.synthetic.main.map_nav_head_emotion_item.view.*

class NavHeaderEmotionAdapter(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<EmotionEntity, MapNavHeadEmotionItemBinding>(
    layoutRes,
    bindingVariabledId
) {
    private val colors = listOf(
        R.drawable.ic_custom_circle_soft_blue,
        R.drawable.ic_custom_circle_cornflower,
        R.drawable.ic_custom_circle_bluegrey,
        R.drawable.ic_custom_circle_very_light_brown,
        R.drawable.ic_custom_circle_warm_grey
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<MapNavHeadEmotionItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        baseViewHolder.itemView.nav_head_emotion_item_color.setImageResource(colors[position])
    }
}