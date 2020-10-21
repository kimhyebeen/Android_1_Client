package com.yapp.picon.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel

class MapViewModel : BaseViewModel() {
    private val _isButtonShown = MutableLiveData<Boolean>()
    val isButtonShown: LiveData<Boolean> get() = _isButtonShown

    init {
        _isButtonShown.value = false
    }

    fun toggleButtonShown() {
        _isButtonShown.value = _isButtonShown.value?.let {
            !it
        }
    }
}