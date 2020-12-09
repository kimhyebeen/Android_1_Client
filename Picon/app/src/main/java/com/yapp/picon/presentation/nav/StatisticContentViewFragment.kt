package com.yapp.picon.presentation.nav

import android.annotation.SuppressLint
import android.app.Application
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavStatisticContentViewBinding
import com.yapp.picon.presentation.base.BaseFragment
import com.yapp.picon.presentation.nav.adapter.EmotionGraphAdapter
import com.yapp.picon.presentation.nav.adapter.EmotionTextAdapter
import com.yapp.picon.presentation.nav.adapter.EmotionViewAdapter
import com.yapp.picon.presentation.nav.adapter.PlaceGraphAdapter
import com.yapp.picon.presentation.nav.repository.EmotionDatabaseRepository
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StatisticContentViewFragment(
    application: Application
): BaseFragment<NavStatisticContentViewBinding, NavViewModel>(
    R.layout.nav_statistic_content_view
) {
    private lateinit var placeAdapter: PlaceGraphAdapter
    private lateinit var emotionAdapter: EmotionGraphAdapter
    private lateinit var emotionTextAdapter: EmotionTextAdapter
    private lateinit var emotionViewAdapter: EmotionViewAdapter
    private lateinit var colorList: List<String>
    private val emotionDatabaseRepository = EmotionDatabaseRepository(application)

    override val vm: NavViewModel by sharedViewModel()

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
        setEmotionViewAdapter()

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

    private fun setEmotionViewAdapter() {
        emotionViewAdapter = EmotionViewAdapter(
            R.layout.statistic_emotion_view_item,
            BR.emoItem
        )
        binding.navStatisticEmotionRv.apply {
            adapter = emotionViewAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
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
            emotionViewAdapter.setItems(it)
            emotionViewAdapter.notifyDataSetChanged()

            it.map { item ->
                item.emotion
            }.let { list ->
                emotionTextAdapter.setItems(list)
                emotionTextAdapter.notifyDataSetChanged()
            }
        })
    }
}