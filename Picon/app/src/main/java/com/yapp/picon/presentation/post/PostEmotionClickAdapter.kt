package com.yapp.picon.presentation.post

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.yapp.picon.presentation.base.BaseRecyclerView

abstract class PostEmotionClickAdapter<B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int,
    private val clickListener: (item: Map<String, String>?) -> Unit
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
            clickListener(items[position])
        }
    }
}