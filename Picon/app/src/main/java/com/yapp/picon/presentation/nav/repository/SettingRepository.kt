package com.yapp.picon.presentation.nav.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingRepository {
    private val _version = MutableLiveData<String>()
    private val _reviewFlag = MutableLiveData<Boolean>()
    private val _withdrawalFlag = MutableLiveData<Boolean>()
    private val _logoutFlag = MutableLiveData<Boolean>()

    val version: LiveData<String> get() = _version
    val reviewFlag: LiveData<Boolean> get() = _reviewFlag
    val withdrawalFlag: LiveData<Boolean> get() = _withdrawalFlag
    val logoutFlag: LiveData<Boolean> get() = _logoutFlag

    init {
        _version.value = "v 1.0.0"
        initializeSettingFlag()
    }

    fun initializeSettingFlag() {
        _reviewFlag.value = false
        _withdrawalFlag.value = false
        _logoutFlag.value = false
    }

    fun clickReview(view: View) {
        _reviewFlag.value = _reviewFlag.value?.let {
            !it
        }
    }

    fun clickWithdrawal(view: View) {
        _withdrawalFlag.value = _withdrawalFlag.value?.let {
            !it
        }
    }

    fun clickLogout(view: View) {
        _logoutFlag.value = _logoutFlag.value?.let {
            !it
        }
    }
}