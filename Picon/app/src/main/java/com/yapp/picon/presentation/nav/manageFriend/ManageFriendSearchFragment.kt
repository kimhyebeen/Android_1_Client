package com.yapp.picon.presentation.nav.manageFriend

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendSearchFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

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
        searchAdapter = ManageFriendFollowAdapter(R.layout.manage_friend_tab_list_item, BR.followItem)
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
}