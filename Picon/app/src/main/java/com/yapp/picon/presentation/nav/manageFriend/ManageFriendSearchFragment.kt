package com.yapp.picon.presentation.nav.manageFriend

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendSearchFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import com.yapp.picon.presentation.profile.FriendProfileActivity

class ManageFriendSearchFragment: BaseFragment<ManageFriendSearchFragmentBinding, ManageFriendViewModel>(
    R.layout.manage_friend_search_fragment
) {
    private lateinit var searchAdapter: ManageFriendFollowAdapter

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
        searchAdapter = ManageFriendFollowAdapter(
            R.layout.manage_friend_tab_list_item,
            BR.followItem,
            {id -> requestFollow(id)},
            {id -> requestUnFollow(id)},
            {identity -> startFriendProfile(identity)}
        )
        binding.manageFriendSearchRv.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        vm.searchList.observe(this, {
            searchAdapter.setItems(it)
            searchAdapter.notifyDataSetChanged()
        })
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