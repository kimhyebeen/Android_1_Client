package com.yapp.picon.presentation.nav.manageFriend

import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendSearchFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageFriendSearchFragment: BaseFragment<ManageFriendSearchFragmentBinding, ManageFriendViewModel>(
    R.layout.manage_friend_search_fragment
) {
    private lateinit var searchAdapter: ManageFriendFollowAdapter
    override val vm: ManageFriendViewModel by viewModel()

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