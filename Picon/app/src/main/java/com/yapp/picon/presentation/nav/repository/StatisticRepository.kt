package com.yapp.picon.presentation.nav.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.broooapps.lineargraphview2.DataModel
import com.yapp.picon.presentation.model.StatisticEmotionGraphItem
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem

class StatisticRepository {
    private val _year = MutableLiveData<Int>()
    private val _month = MutableLiveData<Int>()
    private val _title = MutableLiveData<String>()
    private val _placeList = MutableLiveData<List<StatisticPlaceGraphItem>>()
    private val _emotionList = MutableLiveData<List<StatisticEmotionGraphItem>>()

    init {
        _year.value = 2020
        _month.value = 11
        _title.value = "11월 여행 통계"

        // todo - placeList는 api에서 받아와요.
        _placeList.value = listOf(
            StatisticPlaceGraphItem("서울 관악구", listOf(
                DataModel("감정1", "#8187da", 10),
                DataModel("공백", "#2b2b2b", 1),
                DataModel("감정2", "#6699fc", 30),
                DataModel("공백", "#2b2b2b", 1),
                DataModel("감정3", "#79aed0", 20),
                DataModel("공백", "#2b2b2b", 1),
                DataModel("감정4", "#e6af75", 10),
                DataModel("공백", "#2b2b2b", 1),
                DataModel("감정5", "#9a948b", 30)
            ), 104),
            StatisticPlaceGraphItem("서울 강남구", listOf(
                DataModel("감정1", "#8187da", 10),
                DataModel("감정2", "#6699fc", 20),
                DataModel("감정3", "#79aed0", 30),
                DataModel("감정4", "#e6af75", 40),
                DataModel("감정5", "#9a948b", 10)
            ), 110)
        )

        // todo - emotionList는 api에서 받아와요.
        _emotionList.value = listOf(
            StatisticEmotionGraphItem("새벽 3시", 21),
            StatisticEmotionGraphItem("구름없는 하늘", 11),
            StatisticEmotionGraphItem("아침 이슬", 5),
            StatisticEmotionGraphItem("창문 너머 노을", 4),
            StatisticEmotionGraphItem("잔잔한 밤", 1)
        )
    }

    val year: LiveData<Int> get() = _year
    val month: LiveData<Int> get() = _month
    val title: LiveData<String> get() = _title
    val placeList: LiveData<List<StatisticPlaceGraphItem>> get() = _placeList
    val emotionList: LiveData<List<StatisticEmotionGraphItem>> get() = _emotionList

    fun setPlaceList(list: List<StatisticPlaceGraphItem>) {
        _placeList.value = list
    }

    fun setYear(value: Int) {
        _year.value = value
    }

    fun setMonth(value: Int) {
        _month.value = value
    }

    fun setTitle(value: String) {
        _title.value = value
    }
}