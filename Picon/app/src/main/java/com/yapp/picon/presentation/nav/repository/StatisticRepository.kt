package com.yapp.picon.presentation.nav.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.data.model.EmotionCount
import com.yapp.picon.presentation.model.StatisticDate
import com.yapp.picon.presentation.model.StatisticEmotionGraphItem
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem

class StatisticRepository {
    private val _title = MutableLiveData<String>()
    private val _placeList = MutableLiveData<List<StatisticPlaceGraphItem>>()
    private val _emotionList = MutableLiveData<List<StatisticEmotionGraphItem>>()
    private val _monthList = MutableLiveData<List<StatisticDate>>()
    private val _totalPin = MutableLiveData<String>()

    init {
        _title.value = "11월 여행 통계"
        _totalPin.value = "0 핀"

        // todo - api 데이터에 따라 월별리스트를 설정해줘요.
        _monthList.value = listOf(
            StatisticDate(true, 2020, 9),
            StatisticDate(false, 2020, 8),
            StatisticDate(false, 2020, 7),
            StatisticDate(false, 2020, 6)
        )
    }

    val title: LiveData<String> get() = _title
    val placeList: LiveData<List<StatisticPlaceGraphItem>> get() = _placeList
    val emotionList: LiveData<List<StatisticEmotionGraphItem>> get() = _emotionList
    val totalPin: LiveData<String> get() = _totalPin
    val monthList: LiveData<List<StatisticDate>> get() = _monthList

    fun setPlaceList(list: List<StatisticPlaceGraphItem>) {
        _placeList.value = list
    }

    fun setEmotionList(list: List<StatisticEmotionGraphItem>) {
        _emotionList.value = list
    }

    fun setMonthList(list: List<StatisticDate>) {
        _monthList.value = list
    }

    fun setTotalPin(value: Int) {
        _totalPin.value = "$value 핀"
    }

    fun changeSelected(index: Int) {
        _monthList.value?.get(index)?.let {
            it.selected = !it.selected
        }
    }

    fun setTitle(value: String) {
        _title.value = value
    }
}