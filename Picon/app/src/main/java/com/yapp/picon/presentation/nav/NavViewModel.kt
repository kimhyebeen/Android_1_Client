package com.yapp.picon.presentation.nav

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.model.AddressCount
import com.yapp.picon.data.model.EmotionCount
import com.yapp.picon.data.model.Statistics
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.domain.usecase.LogoutUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.StatisticEmotionGraphItem
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem
import com.yapp.picon.presentation.nav.repository.CustomEmotionRepository
import com.yapp.picon.presentation.nav.repository.SettingRepository
import com.yapp.picon.presentation.nav.repository.StatisticRepository
import kotlinx.coroutines.launch
import java.util.*

class NavViewModel(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {
    val settingRepository = SettingRepository()
    val customRepository = CustomEmotionRepository()
    val statisticRepository = StatisticRepository()

    private val _finishFlag = MutableLiveData<Boolean>()
    private val _toastMsg = MutableLiveData<String>()
    private val _logoutYN = MutableLiveData<Boolean>()

    val toastMsg: LiveData<String> get() = _toastMsg
    val finishFlag: LiveData<Boolean> get() = _finishFlag
    val logoutYN: LiveData<Boolean> get() = _logoutYN


    init {
        _finishFlag.value = false
        _logoutYN.value = false
    }

    fun requestUserInfo(token: String) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestUserInfo(token).let {
                    val date = it.member.createdDate.split('.')
                    statisticRepository.setSignUpDate(date[0].toInt(), date[1].toInt())
                }
            } catch (e: Exception) {
                println("NavViewModel requestUserInfo error - ${e.message}")
                showToast("유저 정보가 존재하지 않습니다.")
            }
        }
    }

    fun requestStatistic(token: String, year: Int, month: Int) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestStatistics(token, year.toString(), month.toString())
                    .let { statistic ->
                        setEmotionGraphList(statistic)
                        setPlaceGraphList(statistic)
                        statisticRepository.setTotalPin(statistic.emotionTotal)
                    }
            } catch (e: Exception) {
                println("NavViewModel error - ${e.message}")
                statisticRepository.initializeGraphData()
                statisticRepository.setTotalPin(0)
                showToast("데이터가 존재하지 않습니다.")
            }
        }
    }

    private fun setEmotionGraphList(statistic: Statistics) {
        if (statistic.emotionCounts.isEmpty()) statisticRepository.initializeGraphData()
        else {
            val emotionList = MutableList(5) {
                StatisticEmotionGraphItem("abc", 0)
            }

            statistic.emotionCounts.map {
                emotionList[getColorIndex(it.emotion)].color = it.emotion
                emotionList[getColorIndex(it.emotion)].count = it.count
            }

            statisticRepository.setEmotionList(emotionList)
        }
    }

    private fun setPlaceGraphList(statistic: Statistics) {
        if (statistic.addressCounts.isEmpty()) statisticRepository.initializeGraphData()
        else {
            val placeList = MutableList(5) {
                StatisticPlaceGraphItem("-", listOf(), 0)
            }

            statistic.addressCounts.let { item ->
                for (i in item.indices) {
                    placeList[i] = StatisticPlaceGraphItem(
                        "${item[i].addrCity} ${item[i].addrGu}",
                        getSortedEmotionList(item[i]),
                        item[i].total
                    )
                }
            }

            statisticRepository.setPlaceList(placeList)
        }
    }

    private fun getSortedEmotionList(item: AddressCount): List<EmotionCount> {
        val emotionList = mutableListOf<EmotionCount>()
        item.emotionCounts.map { emotionList.add(it) }

        Collections.sort(emotionList, Comparator { o1: EmotionCount, o2: EmotionCount ->
            if (getColorIndex(o1.emotion) < getColorIndex(o2.emotion)) return@Comparator -1
            return@Comparator 1
        })

        return emotionList
    }

    private fun getColorIndex(color: String): Int {
        return when (color) {
            "SOFT_BLUE" -> 0
            "CORN_FLOWER" -> 1
            "BLUE_GRAY" -> 2
            "VERY_LIGHT_BROWN" -> 3
            "WARM_GRAY" -> 4
            else -> throw Exception("NavViewModel - getColorIndex - error in color type")
        }
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    fun clickFinishButton(view: View) {
        changeFinishFlag()
    }

    fun changeFinishFlag() {
        _finishFlag.value = _finishFlag.value?.let {
            !it
        }
    }

    fun clickCustomFinishButton(view: View) {
        customRepository.changeCustomFinishFlag()
    }

    fun clickCustomSaveButton(view: View) {
        customRepository.changeCustomSaveFlag()
        _finishFlag.value = _finishFlag.value?.let {
            !it
        }
    }

    fun settingInitializeDialogFlag() {
        settingRepository.initializeSettingFlag()
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _logoutYN.value = true
        }
    }

}