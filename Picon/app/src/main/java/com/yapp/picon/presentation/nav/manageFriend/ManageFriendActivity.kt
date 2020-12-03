package com.yapp.picon.presentation.nav.manageFriend

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.tabs.TabLayoutMediator
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageFriendActivity : BaseActivity<ManageFriendActivityBinding, ManageFriendViewModel>(
    R.layout.manage_friend_activity
) {
    private lateinit var pagerAdapter: ManageFriendPagerAdapter
    override val vm: ManageFriendViewModel by viewModel()

    override fun initViewModel() {
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
        vm.searchText.observe(this, {
            if (it.isEmpty()) {
                binding.manageFriendSearchDeleteButton.visibility = View.GONE
                onHideKeypad()
            } else {
                binding.manageFriendSearchDeleteButton.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.manageVM, vm)

        setPagerAdapter()
    }

    private fun setPagerAdapter() {
        pagerAdapter = ManageFriendPagerAdapter(this)
        binding.manageFriendViewPager.adapter = pagerAdapter

        TabLayoutMediator(
            binding.manageFriendTabLayout,
            binding.manageFriendViewPager
        ) { tab, position ->
            if (position == 0) tab.text = getString(R.string.my_profile_following)
            else tab.text = getString(R.string.my_profile_follower)
        }.attach()
    }

    private fun onHideKeypad() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View = currentFocus ?: View(this)
        view.clearFocus()
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}