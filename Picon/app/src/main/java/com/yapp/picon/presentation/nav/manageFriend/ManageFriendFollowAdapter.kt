package com.yapp.picon.presentation.nav.manageFriend

import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendTabListItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.FollowItem
import kotlinx.android.synthetic.main.manage_friend_tab_list_item.view.*

class ManageFriendFollowAdapter(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
): BaseRecyclerView.BaseAdapter<FollowItem, ManageFriendTabListItemBinding>(
    layoutRes, bindingVariabledId
) {
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<ManageFriendTabListItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        setImageView(baseViewHolder, items[position])
        setFollowButton(baseViewHolder, items[position], position)
    }

    private fun setImageView(
        holder: BaseRecyclerView.BaseViewHolder<ManageFriendTabListItemBinding>,
        item: FollowItem
    ) {
        if (item.image.isNotEmpty()) {
            Glide.with(holder.itemView)
                .load(item.image)
                .centerCrop()
                .circleCrop()
                .into(holder.itemView.friend_list_item_image)
        } else {
            Glide.with(holder.itemView)
                .load(R.drawable.profile_pic)
                .centerCrop()
                .circleCrop()
                .into(holder.itemView.friend_list_item_image)
        }
    }

    private fun setFollowButton(
        holder: BaseRecyclerView.BaseViewHolder<ManageFriendTabListItemBinding>,
        item: FollowItem,
        index: Int
    ) {
        holder.itemView.friend_list_item_follow_button.apply {
            text = if (item.following) {
                setBackgroundResource(R.drawable.bg_un_follow_button)
                "언팔로우"
            } else {
                setBackgroundResource(R.drawable.bg_follow_button)
                "팔로우"
            }

            setOnClickListener {
                if (item.following) {
                    // todo - 팔로우 취소
                    item.following = false
                } else {
                    // todo - 팔로우 적용
                    item.following = true
                }
                notifyItemChanged(index)
            }
        }
    }
}