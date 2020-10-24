package com.yapp.picon.presentation.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

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

    fun requestPost() {
        viewModelScope.launch {
            NetworkModule.yappApi.requestPost("1").let {
                Log.e("aa12", it.toString())
            }
        }
    }

    fun createPost() {
        viewModelScope.launch {

        }
    }
}