package com.yapp.picon.presentation.pingallery

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.yapp.picon.presentation.base.BaseRecyclerView

abstract class PinGalleryClickAdapter<B : ViewDataBinding>(
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
            clickListener(items[position], PinGalleryCode.CLICK_ITEM.code)
        }

//        baseViewHolder.itemView.pin_gallery_item_cb_select.setOnClickListener {
//            clickListener(items[position], SearchCode.CLICK_ITEM_DELETE.code)
//        }
    }
}