package com.yapp.picon.presentation.nav.adapter

import androidx.annotation.LayoutRes
import com.broooapps.lineargraphview2.DataModel
import com.yapp.picon.databinding.PlaceGraphItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem
import kotlinx.android.synthetic.main.place_graph_item.view.*

class PlaceGraphAdapter(
    private val colorList: List<String>,
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

        val dataModelList = mutableListOf<DataModel>()
        items[position].graphItems.map { item ->
            dataModelList.add(
                DataModel(
                    item.color,
                    getColorString(item.color),
                    item.count
                )
            )
        }

        baseViewHolder.itemView.place_graph_view
            .setData(
                dataModelList,
                items[position].total.toFloat()
            )
    }

    private fun getColorString(color: String): String {
        return when(color) {
            "soft_blue" -> colorList[0]
            "corn_flower" -> colorList[1]
            "blue_grey" -> colorList[2]
            "very_light_brown" -> colorList[3]
            else -> colorList[4]
        }
    }
}