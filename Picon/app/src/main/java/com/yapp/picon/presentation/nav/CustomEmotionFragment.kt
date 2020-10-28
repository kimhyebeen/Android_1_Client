package com.yapp.picon.presentation.nav

import android.content.Context
import androidx.fragment.app.viewModels
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavCustomEmotionFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class CustomEmotionFragment: BaseFragment<NavCustomEmotionFragmentBinding, NavViewModel>(
    R.layout.nav_custom_emotion_fragment
) {
    private lateinit var navActivityObserver: NavActivityObserver

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navActivityObserver = context as NavActivityObserver
    }

    override val vm: NavViewModel by viewModels()

    override fun initBinding() {
        binding.setVariable(BR.navVM, vm)
    }

    override fun finishFragment() {
        vm.finishFlag.observe(this, {
            if (it) {
                navActivityObserver.finishNavActivity()
                // TODO("다이얼로그 띄우기")
            }
        })
    }
}