package com.yapp.picon.presentation.setting

import android.os.Bundle
import androidx.activity.viewModels
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.databinding.NavActivityBinding

class SettingActivity: BaseActivity<NavActivityBinding, SettingViewModel>(
    R.layout.nav_activity
) {
    override val vm: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        binding.setVariable(BR.settingVM, vm)
    }
}