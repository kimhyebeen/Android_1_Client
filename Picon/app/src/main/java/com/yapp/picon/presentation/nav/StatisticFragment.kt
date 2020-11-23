package com.yapp.picon.presentation.nav

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import com.yapp.picon.presentation.nav.adapter.MonthListAdapter

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
        transaction.replace(R.id.nav_statistic_frame, StatisticContentViewFragment()).addToBackStack(null).commit()

        setTransrateUpDownAnimation()

        setMonthAdapter()
        binding.navStatisticMonthRecycler.apply {
            adapter = monthAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        // todo - 오늘 날짜 가져와서 requestStatistic(year, month) 요청
        vm.requestStatistic(2020, 11)

        setAnimationToMonthList()
        observeVM()
    }

    private fun setTransrateUpDownAnimation() {
        transrateUp = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        transrateDown = AnimationUtils.loadAnimation(context, R.anim.translate_down)

        transrateUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { }
            override fun onAnimationEnd(animation: Animation?) {
                binding.navStatisticMonthRecycler.visibility = View.INVISIBLE
            }
            override fun onAnimationRepeat(animation: Animation?) { }
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
            { monthListClickEvent() },
            R.layout.month_list_item,
            BR.monthItem
        )
    }

    private fun setAnimationToMonthList() {
        binding.navStatisticAppBar.navStatisticTitleLinearLayout.setOnClickListener {
            monthListClickEvent()
        }
    }

    private fun monthListClickEvent() {
        binding.navStatisticMonthRecycler.let { monthList ->
            if (monthList.isVisible) monthList.startAnimation(transrateUp)
            else {
                monthList.apply {
                    visibility = View.VISIBLE
                    startAnimation(transrateDown)
                }
            }
        }
    }

    private fun observeVM() {
        vm.statisticRepository.monthList.observe(this, {
            monthAdapter.setItems(it)
            monthAdapter.notifyDataSetChanged()

//          todo -  vm.requestStatistic(year, month)
        })
        vm.statisticRepository.title.observe(this, {
            binding.navStatisticAppBar.navStatisticTitleTv.text = it
        })
    }
}