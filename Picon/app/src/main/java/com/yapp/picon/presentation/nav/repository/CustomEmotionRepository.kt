package com.yapp.picon.presentation.nav.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CustomEmotionRepository {
    private val _dialogFinishCancelFlag = MutableLiveData<Boolean>()
    private val _dialogFinishConfirmFlag = MutableLiveData<Boolean>()
    private val _customFinishFlag = MutableLiveData<Boolean>()
    private val _customSaveFlag = MutableLiveData<Boolean>()

    val dialogFinishCancelFlag: LiveData<Boolean> = _dialogFinishCancelFlag
    val dialogFinishConfirmFlag: LiveData<Boolean> = _dialogFinishConfirmFlag
    val customFinishFlag: LiveData<Boolean> get() = _customFinishFlag
    val customSaveFlag: LiveData<Boolean> get() = _customSaveFlag

    init {
        initFinishDialogFlag()

        _customFinishFlag.value = false
        _customSaveFlag.value = false
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