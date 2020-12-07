package com.yapp.picon.presentation.profile

import android.os.Bundle
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.FriendProfileActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendProfileActivity: BaseActivity<FriendProfileActivityBinding, FriendProfileViewModel>(
    R.layout.friend_profile_activity
) {
    override val vm: FriendProfileViewModel by viewModel()

    override fun initViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.fpVM, vm)
    }
}