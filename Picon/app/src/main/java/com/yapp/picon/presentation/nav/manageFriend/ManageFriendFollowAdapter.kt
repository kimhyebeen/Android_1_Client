package com.yapp.picon.presentation.nav.manageFriend

import android.util.Log
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendTabListItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.FollowItem
import kotlinx.android.synthetic.main.manage_friend_tab_list_item.view.*

class ManageFriendFollowAdapter(
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int,
    private val requestFollow: (Int) -> Unit,
    private val requestUnFollow: (Int) -> Unit,
    private val startFriendProfile: (String) -> Unit
): BaseRecyclerView.BaseAdapter<FollowItem, ManageFriendTabListItemBinding>(
    layoutRes, bindingVariabledId
) {
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<ManageFriendTabListItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)

        baseViewHolder.itemView.friend_list_item_image.setOnClickListener {
            startFriendProfile(items[position].email)
        }
        baseViewHolder.itemView.friend_list_item_email_tv.setOnClickListener {
            startFriendProfile(items[position].email)
        }
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
                    item.id?.let {
                        requestUnFollow(it)
                        item.following = false
                    } ?: Log.e("MF_FollowAdapter", "setFollowButton - unfollow - id is null")
                } else {
                    item.id?.let {
                        requestFollow(it)
                        item.following = true
                    } ?: Log.e("MF_FollowAdapter", "setFollowButton - follow - id is null")
                }
                notifyItemChanged(index)
            }
        }
    }
}