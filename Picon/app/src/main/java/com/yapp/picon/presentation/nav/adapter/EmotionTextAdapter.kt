package com.yapp.picon.presentation.nav.adapter

import androidx.annotation.LayoutRes
import com.yapp.picon.databinding.EmotionTextItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import kotlinx.android.synthetic.main.emotion_text_item.view.*

class EmotionTextAdapter(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<String, EmotionTextItemBinding>(
    layoutRes,
    bindingVariabledId
) {
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<EmotionTextItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        baseViewHolder.itemView.emotion_text_tv.text = items[position]
    }
}