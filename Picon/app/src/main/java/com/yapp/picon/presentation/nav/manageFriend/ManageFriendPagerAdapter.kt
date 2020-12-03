package com.yapp.picon.presentation.nav.manageFriend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ManageFriendPagerAdapter(
    fa: FragmentActivity
): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position==0) ManageFriendTabFragment(true)
        else ManageFriendTabFragment(false)
    }

}