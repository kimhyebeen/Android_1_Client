package com.yapp.picon.presentation.nav.manageFriend

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendTabFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import com.yapp.picon.presentation.profile.FriendProfileActivity

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
            BR.followItem,
            {id -> requestFollow(id)},
            {id -> requestUnFollow(id)},
            {identity -> startFriendProfile(identity)}
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

    private fun requestFollow(id: Int) {
        if (vm.token.isNotEmpty()) {
            vm.requestFollow(vm.token, id)
        } else vm.showToast("팔로우 실패")
    }

    private fun requestUnFollow(id: Int) {
        if (vm.token.isNotEmpty()) {
            vm.requestUnFollow(vm.token, id)
        } else vm.showToast("언팔로우 실패")
    }

    private fun startFriendProfile(identity: String) {
        startActivity(
            Intent(context, FriendProfileActivity::class.java)
                .putExtra("identity", identity)
        )
    }
}