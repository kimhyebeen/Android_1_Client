package com.yapp.picon.presentation.post

import android.net.Uri
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.yapp.picon.presentation.base.BaseRecyclerView

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

        baseViewHolder.itemView.setOnClickListener {
            clickListener(items[position])
        }
    }
}