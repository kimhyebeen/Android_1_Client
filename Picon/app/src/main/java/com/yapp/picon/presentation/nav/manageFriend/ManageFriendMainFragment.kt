package com.yapp.picon.presentation.nav.manageFriend

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendMainFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class ManageFriendMainFragment(
    private val application: FragmentActivity
): BaseFragment<ManageFriendMainFragmentBinding, ManageFriendViewModel>(
    R.layout.manage_friend_main_fragment
) {
    private lateinit var pagerAdapter: ManageFriendPagerAdapter

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

        setPagerAdapter()
    }

    override fun onResume() {
        super.onResume()

        if (vm.token.isNotEmpty()) {
            vm.requestFollowingList(vm.token)
            vm.requestFollowerList(vm.token)
        }
    }

    private fun setPagerAdapter() {
        if (binding.manageFriendViewPager.adapter == null) {
            pagerAdapter = ManageFriendPagerAdapter(application)
            binding.manageFriendViewPager.adapter = pagerAdapter

            TabLayoutMediator(
                binding.manageFriendTabLayout,
                binding.manageFriendViewPager
            ) { tab, position ->
                if (position == 0) tab.text = getString(R.string.my_profile_following)
                else tab.text = getString(R.string.my_profile_follower)
            }.attach()
        }
    }
}