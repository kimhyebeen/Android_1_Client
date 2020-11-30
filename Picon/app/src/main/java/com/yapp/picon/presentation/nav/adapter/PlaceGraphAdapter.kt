package com.yapp.picon.presentation.nav.adapter

import androidx.annotation.LayoutRes
import com.broooapps.lineargraphview2.DataModel
import com.yapp.picon.databinding.PlaceGraphItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem
import kotlinx.android.synthetic.main.place_graph_item.view.*
import java.lang.Exception

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
                    item.emotion,
                    getColorString(item.emotion),
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
            "SOFT_BLUE" -> colorList[0]
            "CORN_FLOWER" -> colorList[1]
            "BLUE_GRAY" -> colorList[2]
            "VERY_LIGHT_BROWN" -> colorList[3]
            "WARM_GRAY" -> colorList[4]
            else -> throw Exception("PlaceGraphAdapter - getColorString - color type is wrong")
        }
    }
}