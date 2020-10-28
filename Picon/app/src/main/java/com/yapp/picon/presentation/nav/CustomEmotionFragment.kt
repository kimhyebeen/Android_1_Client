package com.yapp.picon.presentation.nav

import androidx.fragment.app.viewModels
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavCustomEmotionFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class CustomEmotionFragment(
    val finishFunc: () -> Unit
): BaseFragment<NavCustomEmotionFragmentBinding, NavViewModel>(
    R.layout.nav_custom_emotion_fragment
) {
    override val vm: NavViewModel by viewModels()

    override fun initBinding() {
        binding.setVariable(BR.navVM, vm)
    }

    override fun finishFragment() {
        vm.finishFlag.observe(this, {
            if (it) {
                finishFunc()
                // TODO("다이얼로그 띄우기")
            }
        })
    }
}