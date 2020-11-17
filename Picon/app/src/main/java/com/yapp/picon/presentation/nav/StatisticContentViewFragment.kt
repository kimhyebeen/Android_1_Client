package com.yapp.picon.presentation.nav

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavStatisticContentViewBinding
import com.yapp.picon.presentation.base.BaseFragment
import com.yapp.picon.presentation.nav.adapter.PlaceGraphAdapter

class StatisticContentViewFragment: BaseFragment<NavStatisticContentViewBinding, NavViewModel>(
    R.layout.nav_statistic_content_view
) {
    private lateinit var placeAdapter: PlaceGraphAdapter

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

        placeAdapter = PlaceGraphAdapter(R.layout.place_graph_item, BR.item)
        binding.navStatisticPlaceGraphRv.apply {
            adapter = placeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        observeGraphData()
    }

    private fun observeGraphData() {
        vm.statisticRepository.placeList.observe(this, {
            placeAdapter.setItems(it)
        })
    }
}