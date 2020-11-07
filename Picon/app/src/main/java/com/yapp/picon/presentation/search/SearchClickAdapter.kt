package com.yapp.picon.presentation.search

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import kotlinx.android.synthetic.main.search_item.view.*

abstract class SearchClickAdapter<B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int,
    private val clickListener: (item: Map<String, String>?, type: Int) -> Unit
) : BaseRecyclerView.BaseAdapter<Map<String, String>, B>(
    layoutRes,
    bindingVariabledId
) {
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<B>,
        position: Int
    ) {
        items[position].let(baseViewHolder::bind)

        baseViewHolder.itemView.setOnClickListener {
            clickListener(items[position], SearchCode.CLICK_ITEM.code)
        }

        baseViewHolder.itemView.search_item_delete.setOnClickListener {
            clickListener(items[position], SearchCode.CLICK_ITEM_DELETE.code)
        }
    }
}