package com.yapp.picon.presentation.nav.manageFriend

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendTabFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class ManageFriendTabFragment(
    private val isFollowing: Boolean
): BaseFragment<ManageFriendTabFragmentBinding, ManageFriendViewModel>(
    R.layout.manage_friend_tab_fragment
) {
    private lateinit var followAdapter: ManageFriendFollowAdapter
    @Suppress("UNCHECKED_CAST")
    override val vm: ManageFriendViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ManageFriendViewModel() as T
        }
    }

    override fun initBinding() {
    }

    override fun onStart() {
        super.onStart()

        followAdapter = ManageFriendFollowAdapter(
            R.layout.manage_friend_tab_list_item,
            BR.followItem
        )
        binding.manageFriendTabRv.apply {
            adapter = followAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        setAdapterItems()
    }

    override fun onResume() {
        super.onResume()

        if (vm.token.isNotEmpty()) {
            if (isFollowing) vm.requestFollowingList(vm.token)
            else vm.requestFollowerList(vm.token)
        }
    }

    private fun setAdapterItems() {
        if (isFollowing) {
            vm.followingList.observe(this, {
                followAdapter.setItems(it)
                followAdapter.notifyDataSetChanged()
            })
        } else {
            vm.followerList.observe(this, {
                followAdapter.setItems(it)
                followAdapter.notifyDataSetChanged()
            })
        }
    }
}