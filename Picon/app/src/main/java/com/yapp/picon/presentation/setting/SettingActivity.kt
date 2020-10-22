package com.yapp.picon.presentation.setting

import android.os.Bundle
import androidx.activity.viewModels
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.databinding.SettingActivityBinding

class SettingActivity: BaseActivity<SettingActivityBinding, SettingViewModel>(
    R.layout.setting_activity
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