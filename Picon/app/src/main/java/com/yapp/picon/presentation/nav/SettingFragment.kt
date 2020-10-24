package com.yapp.picon.presentation.nav

import android.view.View
import androidx.fragment.app.viewModels
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavSettingFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class SettingFragment(val finishActivity: (View) -> Unit) : BaseFragment<NavSettingFragmentBinding, NavViewModel>(
    R.layout.nav_setting_fragment
) {
    override val vm: NavViewModel by viewModels()

    override fun initBinding() {
        binding.setVariable(BR.setting, this)
    }

    fun finish(view: View) {
        finishActivity(view)
    }
}