package com.yapp.picon.presentation.nav.manageFriend

import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendSearchFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageFriendSearchFragment: BaseFragment<ManageFriendSearchFragmentBinding, ManageFriendViewModel>(
    R.layout.manage_friend_search_fragment
) {
    override val vm: ManageFriendViewModel by viewModel()

    override fun initBinding() {
    }

    override fun onStart() {
        super.onStart()

        // todo - adapter 설정
    }
}