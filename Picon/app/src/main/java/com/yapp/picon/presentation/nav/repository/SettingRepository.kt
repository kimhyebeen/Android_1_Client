package com.yapp.picon.presentation.nav.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingRepository {
    private val _removeAllDataFlag = MutableLiveData<Boolean>()
    private val _reviewFlag = MutableLiveData<Boolean>()
    private val _dialogDismissFlag = MutableLiveData<Boolean>()
    private val _dialogRemoveFlag = MutableLiveData<Boolean>()
    val removeAllDataFlag: LiveData<Boolean> get() = _removeAllDataFlag
    val reviewFlag: LiveData<Boolean> get() = _reviewFlag
    val dialogDismissFlag: LiveData<Boolean> get() = _dialogDismissFlag
    val dialogRemoveFlag: LiveData<Boolean> get() = _dialogRemoveFlag

    init {
        initializeSettingFlag()
    }

    fun initializeSettingFlag() {
        _removeAllDataFlag.value = false
        _reviewFlag.value = false
        _dialogDismissFlag.value = false
        _dialogRemoveFlag.value = false
    }

    fun clickRemoveAllData(view: View) {
        _removeAllDataFlag.value = _removeAllDataFlag.value?.let {
            !it
        }
    }

    fun clickReview(view: View) {
        _reviewFlag.value = _reviewFlag.value?.let {
            !it
        }
    }

    fun clickDialogRemove(view: View) {
        _dialogRemoveFlag.value = _dialogRemoveFlag.value?.let {
            !it
        }
    }

    fun clickDialogDismiss(view: View) {
        _dialogDismissFlag.value = _dialogDismissFlag.value?.let {
            !it
        }
    }
}