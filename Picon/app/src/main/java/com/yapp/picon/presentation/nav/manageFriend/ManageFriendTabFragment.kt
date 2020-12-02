package com.yapp.picon.presentation.nav.manageFriend

import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendTabFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageFriendTabFragment(
    private val isFollowing: Boolean
): BaseFragment<ManageFriendTabFragmentBinding, ManageFriendViewModel>(
    R.layout.manage_friend_tab_fragment
) {
    override val vm: ManageFriendViewModel by viewModel()

    override fun initBinding() {
    }

    override fun onStart() {
        super.onStart()

        // todo - adapter 구현
    }
}