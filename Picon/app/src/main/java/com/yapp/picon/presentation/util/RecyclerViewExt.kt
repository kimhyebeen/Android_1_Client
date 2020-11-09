package com.yapp.picon.presentation.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.picon.presentation.base.BaseRecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("setItems")
fun RecyclerView.setItems(items: List<Map<String, String>>) {
    (this.adapter as? BaseRecyclerView.BaseAdapter<Any, *>)?.run {
        setItems(items)
        notifyDataSetChanged()
    }
}