package com.yapp.picon.presentation.nav

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.picon.R
import com.yapp.picon.databinding.NavStatisticFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class StatisticFragment: BaseFragment<NavStatisticFragmentBinding, NavViewModel>(
    R.layout.nav_statistic_fragment
) {
    private lateinit var transaction: FragmentTransaction
    private lateinit var transrateUp: Animation
    private lateinit var transrateDown: Animation

    @Suppress("UNCHECKED_CAST")
    override val vm: NavViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NavViewModel() as T
        }
    }

    override fun initBinding() {
    }

    override fun onResume() {
        super.onResume()
        // childFragmentManager를 사용해서 nav_statistic_content_view 붙이기
        transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_statistic_frame, StatisticContentViewFragment()).addToBackStack(null).commit()

        transrateUp = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        transrateDown = AnimationUtils.loadAnimation(context, R.anim.translate_down)

        transrateUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.navStatisticMonthLinearLayout.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        /* todo - nav_statistic_title_linear_layout에 adapter 적용
            adapter에서 아이템뷰 클릭하면,
                1. item의 year과 month를 ViewModel에 저장
                2. transrateUp 실행
        */

        setAnimationToMonthList()
        observeVM()
    }

    private fun setAnimationToMonthList() {
        binding.navStatisticAppBar.navStatisticTitleLinearLayout.setOnClickListener {
            binding.navStatisticMonthLinearLayout.let { monthList ->
                if (monthList.isVisible) monthList.startAnimation(transrateUp)
                else {
                    monthList.apply {
                        visibility = View.VISIBLE
                        startAnimation(transrateDown)
                    }
                }
            }
        }
    }

    private fun observeVM() {
        vm.statisticRepository.year.observe(this, {
            setTitleAndGraph(it, vm.statisticRepository.month.value ?: 11)
        })
        vm.statisticRepository.month.observe(this, {
            setTitleAndGraph(vm.statisticRepository.year.value ?: 2020, it)
        })
        vm.statisticRepository.title.observe(this, {
            binding.navStatisticAppBar.navStatisticTitleTv.text = it
        })
    }

    private fun setTitleAndGraph(year: Int, month: Int) {
        // todo - title 재설정 (vm.statistic.title = month월 여행 통계)
        // todo - 그래프 데이터 재설정
    }
}