package com.yapp.picon.presentation.nav.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CustomEmotionRepository {
    private val _dialogCancelFlag = MutableLiveData<Boolean>()
    private val _dialogConfirmFlag = MutableLiveData<Boolean>()

    init {
        setDialogInit()
    }

    val dialogCancelFlag: LiveData<Boolean> get() = _dialogCancelFlag
    val dialogConfirmFlag: LiveData<Boolean> get() = _dialogConfirmFlag

    fun setDialogInit() {
        _dialogCancelFlag.value = false
        _dialogConfirmFlag.value = false
    }

    fun clickDialogCancel(view: View) {
        _dialogCancelFlag.value = _dialogCancelFlag.value?.let {
            !it
        }
    }

    fun clickDialogConfirm(view: View) {
        _dialogConfirmFlag.value = _dialogConfirmFlag.value?.let {
            !it
        }
    }
}