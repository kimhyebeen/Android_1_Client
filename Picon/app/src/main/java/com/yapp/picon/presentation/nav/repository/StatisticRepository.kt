package com.yapp.picon.presentation.nav.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StatisticRepository {
    private val _year = MutableLiveData<Int>()
    private val _month = MutableLiveData<Int>()
    private val _title = MutableLiveData<String>()

    init {
        _year.value = 2020
        _month.value = 11
        _title.value = "11월 여행 통계"
    }

    val year: LiveData<Int> get() = _year
    val month: LiveData<Int> get() = _month
    val title: LiveData<String> get() = _title
}