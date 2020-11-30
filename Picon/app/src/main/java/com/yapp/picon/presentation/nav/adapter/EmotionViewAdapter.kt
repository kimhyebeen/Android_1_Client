package com.yapp.picon.presentation.nav.adapter

import androidx.annotation.LayoutRes
import com.yapp.picon.R
import com.yapp.picon.databinding.StatisticEmotionViewItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.EmotionEntity
import kotlinx.android.synthetic.main.statistic_emotion_view_item.view.*

class EmotionViewAdapter(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<EmotionEntity, StatisticEmotionViewItemBinding>(
    layoutRes,
    bindingVariabledId
) {

    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<StatisticEmotionViewItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        baseViewHolder.itemView.statistic_emotion_color_iv.setImageResource(
            getResource(items[position].color)
        )
    }

    private fun getResource(color: String): Int {
        return when(color) {
            "SOFT_BLUE" -> R.drawable.ic_custom_circle_soft_blue
            "CORN_FLOWER" -> R.drawable.ic_custom_circle_cornflower
            "BLUE_GRAY" -> R.drawable.ic_custom_circle_bluegrey
            "VERY_LIGHT_BROWN" -> R.drawable.ic_custom_circle_very_light_brown
            "WARM_GRAY" -> R.drawable.ic_custom_circle_warm_grey
            else -> throw Exception("EmotionGridAdapter - getResource - color type is wrong.")
        }
    }
}