package com.yapp.picon.presentation.nav.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.annotation.LayoutRes
import com.yapp.picon.databinding.MonthListItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.StatisticDate
import kotlinx.android.synthetic.main.month_list_item.view.*

class MonthListAdapter(
    private val textColor: List<String>,
    private val changeTitle: (String) -> Unit,
    private val changeViewModelMonthData: (Int, Int) -> Unit,
    private val clickEvent: (Int, Int, Boolean) -> Unit,
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<StatisticDate, MonthListItemBinding>(
    layoutRes,
    bindingVariabledId
) {
    private var selectedIndex = 0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<MonthListItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        if (items[position].month < 10) baseViewHolder.itemView.month_list_tv.text = "${items[position].year}년 0${items[position].month}월"
        else baseViewHolder.itemView.month_list_tv.text = "${items[position].year}년 ${items[position].month}월"

        if (items[position].selected) {
            baseViewHolder.itemView.month_list_tv.setTextColor(Color.parseColor(textColor[0]))
            changeTitle("${items[position].month}월 여행 통계")
        }
        else baseViewHolder.itemView.month_list_tv.setTextColor(Color.parseColor(textColor[1]))

        baseViewHolder.itemView.setOnClickListener {
            changeViewModelMonthData(selectedIndex, position)
            notifyItemChanged(selectedIndex)
            notifyItemChanged(position)
            selectedIndex = position

            clickEvent(items[position].year, items[position].month, true)
        }
    }
}