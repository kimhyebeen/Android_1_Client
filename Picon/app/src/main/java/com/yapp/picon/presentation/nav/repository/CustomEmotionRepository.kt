package com.yapp.picon.presentation.nav.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.R
import com.yapp.picon.presentation.model.CustomEmotion

class CustomEmotionRepository {
    private var _items = MutableLiveData<List<CustomEmotion>>()
    private val _dialogFinishCancelFlag = MutableLiveData<Boolean>()
    private val _dialogFinishConfirmFlag = MutableLiveData<Boolean>()
    private val _customFinishFlag = MutableLiveData<Boolean>()
    private val _customSaveFlag = MutableLiveData<Boolean>()

    val items: LiveData<List<CustomEmotion>> = _items
    val dialogFinishCancelFlag: LiveData<Boolean> = _dialogFinishCancelFlag
    val dialogFinishConfirmFlag: LiveData<Boolean> = _dialogFinishConfirmFlag
    val customFinishFlag: LiveData<Boolean> get() = _customFinishFlag
    val customSaveFlag: LiveData<Boolean> get() = _customSaveFlag

    init {
        initFinishDialogFlag()

        _customFinishFlag.value = false
        _customSaveFlag.value = false

        // TODO("서버에서 item 내용 가져오기")
        _items.value = listOf(
            CustomEmotion(R.drawable.ic_custom_circle_soft_blue, "새벽 3시"),
            CustomEmotion(R.drawable.ic_custom_circle_cornflower, "구름없는 하늘"),
            CustomEmotion(R.drawable.ic_custom_circle_bluegrey, "아침 이슬"),
            CustomEmotion(R.drawable.ic_custom_circle_very_light_brown, "창문 너머 노을"),
            CustomEmotion(R.drawable.ic_custom_circle_warm_grey, "잔잔한 밤")
        )
    }

    fun initFinishDialogFlag() {
        _dialogFinishCancelFlag.value = false
        _dialogFinishConfirmFlag.value = false
    }

    fun changeCustomFinishFlag() {
        _customFinishFlag.value = _customFinishFlag.value?.let {
            !it
        }
    }

    fun changeCustomSaveFlag() {
        _customSaveFlag.value = _customSaveFlag.value?.let {
            !it
        }
    }

    fun setItems(index: Int, value: String) {
        _items.value?.get(index)?.text = value
    }

    fun clickFinishDialogCancel(view: View) {
        _dialogFinishCancelFlag.value = _dialogFinishCancelFlag.value?.let {
            !it
        }
    }

    fun clickFinishDialogConfirm(view: View) {
        _dialogFinishConfirmFlag.value = _dialogFinishConfirmFlag.value?.let {
            !it
        }
    }
}