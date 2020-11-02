package com.yapp.picon.presentation.nav

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel

class NavViewModel: BaseViewModel() {
    private val _finishFlag = MutableLiveData<Boolean>()
    val finishFlag: LiveData<Boolean> get() = _finishFlag

    init {
        _finishFlag.value = false
    }

    fun clickFinishButton(view: View) {
        _finishFlag.value = _finishFlag.value?.let {
            !it
        }
    }
}