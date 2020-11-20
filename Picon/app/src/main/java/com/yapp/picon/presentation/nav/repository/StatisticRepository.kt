package com.yapp.picon.presentation.nav.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.model.ListItemForPlaceGraph
import com.yapp.picon.presentation.model.StatisticDate
import com.yapp.picon.presentation.model.StatisticEmotionGraphItem
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem

class StatisticRepository {
    private val _title = MutableLiveData<String>()
    private val _placeList = MutableLiveData<List<StatisticPlaceGraphItem>>()
    private val _emotionList = MutableLiveData<List<StatisticEmotionGraphItem>>()
    private val _monthList = MutableLiveData<List<StatisticDate>>()

    init {
        _title.value = "11월 여행 통계"

        // todo - placeList는 api에서 받아와요.
        _placeList.value = listOf(
            StatisticPlaceGraphItem("서울 강남구", listOf(
                ListItemForPlaceGraph("soft_blue", 10),
                ListItemForPlaceGraph("corn_flower", 6),
                ListItemForPlaceGraph("very_light_brown", 4)
            ), 19),
            StatisticPlaceGraphItem("서울 용산구", listOf(
                ListItemForPlaceGraph("soft_blue", 4),
                ListItemForPlaceGraph("corn_flower", 3),
                ListItemForPlaceGraph("blue_grey", 2),
                ListItemForPlaceGraph("very_light_brown", 2)
            ), 11),
            StatisticPlaceGraphItem("서울 강남구", listOf(
                ListItemForPlaceGraph("soft_blue", 3),
                ListItemForPlaceGraph("corn_flower", 3),
                ListItemForPlaceGraph("warm_grey", 2)
            ), 8),
            StatisticPlaceGraphItem("서울 강남구", listOf(
                ListItemForPlaceGraph("corn_flower", 1),
                ListItemForPlaceGraph("blue_grey", 1),
                ListItemForPlaceGraph("very_light_brown", 1)
            ), 3),
            StatisticPlaceGraphItem("서울 강남구", listOf(
                ListItemForPlaceGraph("soft_blue", 1)
            ), 1)
        )

        // todo - emotionList는 api에서 받아와요.
        _emotionList.value = listOf(
            StatisticEmotionGraphItem("새벽 3시", 21),
            StatisticEmotionGraphItem("구름없는 하늘", 11),
            StatisticEmotionGraphItem("아침 이슬", 5),
            StatisticEmotionGraphItem("창문 너머 노을", 4),
            StatisticEmotionGraphItem("잔잔한 밤", 1)
        )

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

    fun changeSelected(index: Int) {
        _monthList.value?.get(index)?.let {
            it.selected = !it.selected
        }
    }

    fun setTitle(value: String) {
        _title.value = value
    }
}