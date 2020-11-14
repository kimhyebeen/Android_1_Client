package com.yapp.picon.presentation.nav

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.picon.R
import com.yapp.picon.databinding.NavStatisticFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class StatisticFragment: BaseFragment<NavStatisticFragmentBinding, NavViewModel>(
    R.layout.nav_statistic_fragment
) {
    private val transaction = childFragmentManager.beginTransaction()

    @Suppress("UNCHECKED_CAST")
    override val vm: NavViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NavViewModel() as T
        }
    }

    override fun initBinding() {
//        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // childFragmentManager를 사용해서 nav_statistic_content_view 붙이기
    }
}