package com.yapp.picon.presentation.profile

import android.os.Bundle
import com.bumptech.glide.Glide
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
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
        vm.image.observe(this, {
            setProfileImage(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.fpVM, vm)

        val identity = intent.getStringExtra("identity")
        vm.requestFriendProfile(identity)
    }

    private fun setProfileImage(value: String) {
        if (value.isNotEmpty()) {
            Glide.with(this)
                .load(value)
                .centerCrop()
                .circleCrop()
                .into(binding.friendProfileUserImage)
        } else {
            Glide.with(this)
                .load(R.drawable.profile_pic)
                .centerCrop()
                .circleCrop()
                .into(binding.friendProfileUserImage)
        }
    }
}