package com.yapp.picon.presentation.nav.manageFriend

import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendMainFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageFriendMainFragment(
    private val application: FragmentActivity
): BaseFragment<ManageFriendMainFragmentBinding, ManageFriendViewModel>(
    R.layout.manage_friend_main_fragment
) {
    private lateinit var pagerAdapter: ManageFriendPagerAdapter
    override val vm: ManageFriendViewModel by viewModel()

    override fun initBinding() {
    }

    override fun onStart() {
        super.onStart()

        setPagerAdapter()
    }

    private fun setPagerAdapter() {
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