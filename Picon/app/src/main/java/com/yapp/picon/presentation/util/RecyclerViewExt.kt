package com.yapp.picon.presentation.util

import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.Pin

@Suppress("UNCHECKED_CAST")
@BindingAdapter("setItems")
fun RecyclerView.setItems(items: List<Map<String, String>>) {
    (this.adapter as? BaseRecyclerView.BaseAdapter<Any, *>)?.run {
        setItems(items)
        notifyDataSetChanged()
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("setUris")
fun RecyclerView.setUris(uris: List<Uri>) {
    (this.adapter as? BaseRecyclerView.BaseAdapter<Any, *>)?.run {
        setItems(uris)
        notifyDataSetChanged()
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("setPinItems")
fun RecyclerView.setPinItems(pinItems: List<Pin>) {
    (this.adapter as? BaseRecyclerView.BaseAdapter<Any, *>)?.run {
        setItems(pinItems)
        notifyDataSetChanged()
    }
}