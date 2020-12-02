package com.yapp.picon.presentation.nav.manageFriend

import android.os.Bundle
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageFriendActivity : BaseActivity<ManageFriendActivityBinding, ManageFriendViewModel>(
    R.layout.manage_friend_activity
) {
    override val vm: ManageFriendViewModel by viewModel()

    override fun initViewModel() {
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.setVariable(BR.manageVM, vm)
    }
}