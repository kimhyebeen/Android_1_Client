package com.yapp.picon.presentation.nav.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.model.StatisticDate
import com.yapp.picon.presentation.model.StatisticEmotionGraphItem
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem

class StatisticRepository {
    private val _signUpDate = MutableLiveData<StatisticDate>()
    private val _title = MutableLiveData<String>()
    private val _placeList = MutableLiveData<List<StatisticPlaceGraphItem>>()
    private val _emotionList = MutableLiveData<List<StatisticEmotionGraphItem>>()
    private val _monthList = MutableLiveData<List<StatisticDate>>()
    private val _totalPin = MutableLiveData<String>()

    init {
        _title.value = "11월 여행 통계"
        _totalPin.value = "0 핀"
    }

    val title: LiveData<String> get() = _title
    val placeList: LiveData<List<StatisticPlaceGraphItem>> get() = _placeList
    val emotionList: LiveData<List<StatisticEmotionGraphItem>> get() = _emotionList
    val totalPin: LiveData<String> get() = _totalPin
    val monthList: LiveData<List<StatisticDate>> get() = _monthList
    val signUpDate: LiveData<StatisticDate> get() = _signUpDate

    fun initializeGraphData() {
        _emotionList.value = mutableListOf()
        _placeList.value = mutableListOf()
    }

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

    fun setSignUpDate(year: Int, month: Int) {
        _signUpDate.value = StatisticDate(false, year, month)
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