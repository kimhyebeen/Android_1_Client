package com.yapp.picon.presentation.nav.adapter

import androidx.annotation.LayoutRes
import com.broooapps.lineargraphview2.DataModel
import com.yapp.picon.R
import com.yapp.picon.databinding.EmotionGraphItemBinding
import com.yapp.picon.databinding.PlaceGraphItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.StatisticEmotionGraphItem
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem
import kotlinx.android.synthetic.main.emotion_graph_item.view.*
import kotlinx.android.synthetic.main.place_graph_item.view.*

class EmotionGraphAdapter(
    private val colorList: List<String>,
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<StatisticEmotionGraphItem, EmotionGraphItemBinding>(
    layoutRes,
    bindingVariabledId
) {
    private var maxCount = 0

    fun setMaxCount(value: Int) {
        maxCount = value
    }

    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<EmotionGraphItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        baseViewHolder.itemView.emotion_graph_view
            .setData(
                listOf(
                    DataModel(
                        items[position].color,
                        colorList[position],
                        items[position].count
                    )
                ),
                maxCount.toFloat()
            )
    }
}