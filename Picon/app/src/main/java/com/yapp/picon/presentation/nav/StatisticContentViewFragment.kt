package com.yapp.picon.presentation.nav

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavStatisticContentViewBinding
import com.yapp.picon.presentation.base.BaseFragment
import com.yapp.picon.presentation.nav.adapter.EmotionGraphAdapter
import com.yapp.picon.presentation.nav.adapter.PlaceGraphAdapter

class StatisticContentViewFragment: BaseFragment<NavStatisticContentViewBinding, NavViewModel>(
    R.layout.nav_statistic_content_view
) {
    private lateinit var placeAdapter: PlaceGraphAdapter
    private lateinit var emotionAdapter: EmotionGraphAdapter

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

        setEmotionAdapter()
        setPlaceAdapter()
        observeGraphData()
    }

    @SuppressLint("ResourceType")
    private fun setEmotionAdapter() {
        emotionAdapter = EmotionGraphAdapter(
            listOf(
                getString(R.color.soft_blue),
                getString(R.color.cornflower),
                getString(R.color.bluegrey),
                getString(R.color.very_light_brown),
                getString(R.color.warm_grey)
            ),
            R.layout.emotion_graph_item,
            BR.emotionItem
        )
        binding.navStatisticEmotionGraphRv.apply {
            adapter = emotionAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setPlaceAdapter() {
        placeAdapter = PlaceGraphAdapter(R.layout.place_graph_item, BR.item)
        binding.navStatisticPlaceGraphRv.apply {
            adapter = placeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeGraphData() {
        vm.statisticRepository.placeList.observe(this, {
            placeAdapter.setItems(it)
        })
        vm.statisticRepository.emotionList.observe(this, {
            emotionAdapter.setItems(it)
            var max = 0
            it.map { item ->
                if (max < item.count) max = item.count
            }
            emotionAdapter.setMaxCount(max)

            var sum = 0
            it.map { item ->
                sum += item.count
            }
            binding.navStatisticPinNumberTv.text = "$sum 핀"
        })
    }
}