package com.yapp.picon.presentation.nav

import android.annotation.SuppressLint
import android.app.Application
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavStatisticContentViewBinding
import com.yapp.picon.presentation.base.BaseFragment
import com.yapp.picon.presentation.nav.adapter.EmotionGraphAdapter
import com.yapp.picon.presentation.nav.adapter.EmotionGridAdapter
import com.yapp.picon.presentation.nav.adapter.EmotionTextAdapter
import com.yapp.picon.presentation.nav.adapter.PlaceGraphAdapter
import com.yapp.picon.presentation.nav.repository.EmotionDatabaseRepository

class StatisticContentViewFragment(
    application: Application
): BaseFragment<NavStatisticContentViewBinding, NavViewModel>(
    R.layout.nav_statistic_content_view
) {
    private lateinit var placeAdapter: PlaceGraphAdapter
    private lateinit var emotionAdapter: EmotionGraphAdapter
    private lateinit var emotionTextAdapter: EmotionTextAdapter
    private lateinit var emotionGridAdapter: EmotionGridAdapter
    private lateinit var colorList: List<String>
    private val emotionDatabaseRepository = EmotionDatabaseRepository(application)

    @Suppress("UNCHECKED_CAST")
    override val vm: NavViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NavViewModel() as T
        }
    }

    override fun initBinding() {
    }

    @SuppressLint("ResourceType")
    override fun onStart() {
        super.onStart()
        colorList = listOf(
            getString(R.color.soft_blue),
            getString(R.color.cornflower),
            getString(R.color.bluegrey),
            getString(R.color.very_light_brown),
            getString(R.color.warm_grey)
        )

        setEmotionAdapter()
        setPlaceAdapter()
        setEmotionTextAdapter()
        setEmotionGridAdapter()
        observeGraphData()
    }

    private fun setEmotionAdapter() {
        emotionAdapter = EmotionGraphAdapter(
            colorList,
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
        placeAdapter = PlaceGraphAdapter(
            colorList,
            R.layout.place_graph_item,
            BR.placeItem
        )
        binding.navStatisticPlaceGraphRv.apply {
            adapter = placeAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setEmotionTextAdapter() {
        emotionTextAdapter = EmotionTextAdapter(
            R.layout.emotion_text_item,
            BR.textItem
        )
        binding.navStatisticEmotionTextRv.apply {
            adapter = emotionTextAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setEmotionGridAdapter() {
        emotionGridAdapter = EmotionGridAdapter()
        binding.navStatisticEmotionGrid.apply {
            adapter = emotionGridAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeGraphData() {
        vm.statisticRepository.placeList.observe(this, {
            placeAdapter.setItems(it)
            placeAdapter.notifyDataSetChanged()
        })

        vm.statisticRepository.emotionList.observe(this, {
            var max = 0
            it.map { item -> if (max < item.count) max = item.count }

            emotionAdapter.apply {
                setMaxCount(max)
                setItems(it)
                notifyDataSetChanged()
            }
        })

        vm.statisticRepository.totalPin.observe(this) {
            binding.navStatisticPinNumberTv.text = it
        }

        emotionDatabaseRepository.getAll().observe(this, {
            emotionGridAdapter.setItems(it)

            it.map { item ->
                item.emotion
            }.let { list ->
                emotionTextAdapter.setItems(list)
                emotionTextAdapter.notifyDataSetChanged()
            }
        })
    }
}