package com.yapp.picon.presentation.post

import android.net.Uri
import androidx.annotation.LayoutRes
import androidx.core.graphics.toColorInt
import androidx.databinding.ViewDataBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import kotlinx.android.synthetic.main.post_picture_item.view.*

abstract class PostPictureClickAdapter<B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int,
    private val clickListener: (item: Uri?) -> Unit
) : BaseRecyclerView.BaseAdapter<Uri, B>(
    layoutRes,
    bindingVariabledId
) {

    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<B>,
        position: Int
    ) {
        items[position].let(baseViewHolder::bind)
        if (position == 0) {
            baseViewHolder.itemView.post_picture_item_iv.run {
                setPadding(14, 14, 14, 14)
                //todo hardcoding
                setBackgroundColor("#6699fc".toColorInt())
            }
        }
        baseViewHolder.itemView.setOnClickListener {
            clickListener(items[position])
        }
    }
}