package com.yapp.picon.presentation.nav

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavStatisticFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import com.yapp.picon.presentation.model.StatisticDate
import com.yapp.picon.presentation.nav.adapter.MonthListAdapter
import java.util.*

class StatisticFragment: BaseFragment<NavStatisticFragmentBinding, NavViewModel>(
    R.layout.nav_statistic_fragment
) {
    private lateinit var transaction: FragmentTransaction
    private lateinit var transrateUp: Animation
    private lateinit var transrateDown: Animation
    private lateinit var monthAdapter: MonthListAdapter

    @Suppress("UNCHECKED_CAST")
    override val vm: NavViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NavViewModel() as T
        }
    }

    override fun initBinding() {
    }

    override fun onStart() {
        super.onStart()
        // childFragmentManager를 사용해서 nav_statistic_content_view 붙이기
        transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_statistic_frame, StatisticContentViewFragment()).addToBackStack(
            null
        ).commit()

        setTransrateUpDownAnimation()
        setMonthAdapter()
        setAllDatesInMonthList()

        binding.navStatisticMonthRecycler.apply {
            adapter = monthAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        binding.navStatisticAppBar.navStatisticTitleLinearLayout.setOnClickListener {
            monthListClickEvent(0, 0, false)
        }

        getTodayDate().let { vm.requestStatistic(it.year, it.month) }
        observeVM()
    }

    private fun setTransrateUpDownAnimation() {
        transrateUp = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        transrateDown = AnimationUtils.loadAnimation(context, R.anim.translate_down)

        transrateUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.navStatisticMonthRecycler.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    @SuppressLint("ResourceType")
    private fun setMonthAdapter() {
        monthAdapter = MonthListAdapter(
            listOf(getString(R.color.cornflower), getString(R.color.very_light_pink_two)),
            { str -> vm.statisticRepository.setTitle(str) },
            { pre, cur ->
                vm.statisticRepository.changeSelected(pre)
                vm.statisticRepository.changeSelected(cur)
            },
            { year, month, flag -> monthListClickEvent(year, month, flag) },
            R.layout.month_list_item,
            BR.monthItem
        )
    }

    private fun getTodayDate(): StatisticDate {
        Calendar.getInstance().let {
            return StatisticDate(true,
                it.get(Calendar.YEAR),
                it.get(Calendar.MONTH)+1
            )
        }
    }

    private fun setAllDatesInMonthList() {
        // todo - 현재 연도+월 부터 회원가입 연도+월까지 vm.setMonthList하기
        vm.statisticRepository.setMonthList(
            listOf(
                StatisticDate(true, 2020, 11),
                StatisticDate(false, 2020, 10),
                StatisticDate(false, 2020, 9),
                StatisticDate(false, 2020, 8),
                StatisticDate(false, 2020, 7),
                StatisticDate(false, 2020, 6)
            )
        )
    }

    private fun monthListClickEvent(year: Int, month: Int, flag: Boolean) {
        binding.navStatisticMonthRecycler.let { monthList ->
            if (monthList.isVisible) monthList.startAnimation(transrateUp)
            else {
                monthList.apply {
                    visibility = View.VISIBLE
                    startAnimation(transrateDown)
                }
            }
            if (flag) {
                vm.requestStatistic(year, month)
            }
        }
    }

    private fun observeVM() {
        vm.statisticRepository.monthList.observe(this, {
            monthAdapter.setItems(it)
            monthAdapter.notifyDataSetChanged()

            if (it.size > 5) {
                binding.navStatisticMonthRecycler.layoutParams = getLayoutParams(true)
            } else {
                binding.navStatisticMonthRecycler.layoutParams = getLayoutParams(false)
            }
        })
        vm.statisticRepository.title.observe(this, {
            binding.navStatisticAppBar.navStatisticTitleTv.text = it
        })
    }

    private fun getLayoutParams(flag: Boolean): CoordinatorLayout.LayoutParams {
        val layoutParams = if (flag) {
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                getPx(330f)
            )
        } else {
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                CoordinatorLayout.LayoutParams.WRAP_CONTENT
            )
        }

        return layoutParams.apply {
            setMargins(0, getPx(68f),0,0)
            gravity = Gravity.CENTER_HORIZONTAL
        }
    }

    private fun getPx(dp: Float): Int {
        val displayMetrics = resources.displayMetrics

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()
    }
}