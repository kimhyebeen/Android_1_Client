package com.yapp.picon.presentation.nav

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.StatisticEmotionGraphItem
import com.yapp.picon.presentation.model.StatisticPlaceGraphItem
import com.yapp.picon.presentation.nav.repository.CustomEmotionRepository
import com.yapp.picon.presentation.nav.repository.SettingRepository
import com.yapp.picon.presentation.nav.repository.StatisticRepository
import kotlinx.coroutines.launch

class NavViewModel: BaseViewModel() {
    val settingRepository = SettingRepository()
    val customRepository = CustomEmotionRepository()
    val statisticRepository = StatisticRepository()

    private val _finishFlag = MutableLiveData<Boolean>()

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    val finishFlag: LiveData<Boolean> get() = _finishFlag

    init {
        _finishFlag.value = false
    }

    fun requestStatistic(year: Int, month: Int) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestStatistics(year.toString(), month.toString()).let { statistic ->
                    val emotionList = MutableList(5) {
                        StatisticEmotionGraphItem("abc", 0)
                    }

                    // todo - 데이터베이스에서 감정이름 얻어와서 emotionList[getColorIndex(it.emotion)].color에 넣어주기

                    statistic.emotionCounts.map {
                        emotionList[getColorIndex(it.emotion)].color = it.emotion // todo - 데이터베이스 작업 이후 삭제
                        emotionList[getColorIndex(it.emotion)].count = it.count
                    }

                    statisticRepository.setEmotionList(emotionList)
                    statisticRepository.setTotalPin(statistic.emotionTotal)
                }
            } catch (e: Exception) {
                println("NavViewModel - ${e.message}")
                showToast("통신 오류")
            }
        }
    }

    private fun getColorIndex(color: String): Int {
        return when(color) {
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
}