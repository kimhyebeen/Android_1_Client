package com.yapp.picon.presentation.nav.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yapp.picon.R
import com.yapp.picon.presentation.model.EmotionEntity
import kotlinx.android.synthetic.main.statistic_emotion_grid_item.view.*

class EmotionGridAdapter: BaseAdapter() {
    private var items = listOf<EmotionEntity>()

    fun setItems(list: List<EmotionEntity>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): EmotionEntity = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: View = convertView?.let { it } ?: run {
            val inflater: LayoutInflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.statistic_emotion_grid_item, parent, false)
        }

        itemView.statistic_emotion_grid_color_iv.setImageResource(
            getResource(items[position].color)
        )
        itemView.statistic_emotion_grid_tv.text = items[position].emotion

        return itemView
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