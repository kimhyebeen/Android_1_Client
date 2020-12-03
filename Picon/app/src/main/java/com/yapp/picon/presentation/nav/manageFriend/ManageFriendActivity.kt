package com.yapp.picon.presentation.nav.manageFriend

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.ManageFriendActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageFriendActivity : BaseActivity<ManageFriendActivityBinding, ManageFriendViewModel>(
    R.layout.manage_friend_activity
) {
    private lateinit var mainFragment: ManageFriendMainFragment
    private val searchFragment = ManageFriendSearchFragment()
    override val vm: ManageFriendViewModel by viewModel()

    override fun initViewModel() {
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
        vm.searchText.observe(this, { str ->
            if (str.isEmpty()) {
                binding.manageFriendSearchDeleteButton.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.manage_friend_frame_layout, mainFragment)
                    .addToBackStack(null).commit()

                onHideKeypad()
            } else {
                if (binding.manageFriendSearchDeleteButton.visibility == View.GONE) {
                    binding.manageFriendSearchDeleteButton.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.manage_friend_frame_layout, searchFragment)
                        .addToBackStack(null).commit()
                }

                Handler().postDelayed({
                    if (str == binding.manageFriendSearchEt.text.toString()) {
                        vm.requestSearch(str)
                    }
                },500)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.manageVM, vm)

        mainFragment = ManageFriendMainFragment(this)
    }

    private fun onHideKeypad() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View = currentFocus ?: View(this)
        view.clearFocus()
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}