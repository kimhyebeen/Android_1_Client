package com.yapp.picon.presentation.nav

import androidx.fragment.app.viewModels
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavCustomEmotionFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class CustomEmotionFragment: BaseFragment<NavCustomEmotionFragmentBinding, NavViewModel>(
    R.layout.nav_custom_emotion_fragment
) {
    override val vm: NavViewModel by viewModels()

    override fun initBinding() {
        binding.setVariable(BR.setting, this)
    }
}