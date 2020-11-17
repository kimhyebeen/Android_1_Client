package com.yapp.picon.presentation.nav.adapter

import androidx.annotation.LayoutRes
import com.yapp.picon.databinding.PlaceGraphItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem
import kotlinx.android.synthetic.main.place_graph_item.view.*

class PlaceGraphAdapter(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<StatisticPlaceGraphItem, PlaceGraphItemBinding>(
    layoutRes,
    bindingVariabledId
) {
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<PlaceGraphItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        baseViewHolder.itemView.place_graph_view
            .setData(
                items[position].graphItems,
                items[position].size.toFloat()
            )
    }
}