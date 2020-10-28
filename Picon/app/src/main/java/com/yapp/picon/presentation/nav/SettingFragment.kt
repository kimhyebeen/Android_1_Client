package com.yapp.picon.presentation.nav

import androidx.fragment.app.viewModels
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavSettingFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class SettingFragment(
    val finishFunc: () -> Unit
): BaseFragment<NavSettingFragmentBinding, NavViewModel>(
    R.layout.nav_setting_fragment
) {
    override val vm: NavViewModel by viewModels()

    override fun initBinding() {
        binding.setVariable(BR.navVM, vm)
    }

    override fun finishFragment() {
        vm.finishFlag.observe(this, {
            if (it) finishFunc()
        })
    }
}