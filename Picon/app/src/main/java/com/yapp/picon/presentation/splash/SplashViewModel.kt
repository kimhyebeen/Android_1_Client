package com.yapp.picon.presentation.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.LoadAccessTokenUseCase
import com.yapp.picon.domain.usecase.LoadFirstUseCase
import com.yapp.picon.domain.usecase.SaveFirstUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val loadFirstUseCase: LoadFirstUseCase,
    private val saveFirstUseCase: SaveFirstUseCase,
    private val loadAccessTokenUseCase: LoadAccessTokenUseCase
) : BaseViewModel() {

    private val _checkYN = MutableLiveData<Boolean>()
    val checkYN: LiveData<Boolean> get() = _checkYN

    private val _firstYN = MutableLiveData<Boolean>()
    val firstYN: LiveData<Boolean> get() = _firstYN

    private val _loginYN = MutableLiveData<Boolean>()
    val loginYN: LiveData<Boolean> get() = _loginYN

    init {
        _checkYN.value = false
        _firstYN.value = true
        _loginYN.value = false
    }

    fun check() {
        viewModelScope.launch {
            loadFirstUseCase().let {
                Log.e("aa12", "loadFirstUseCase : $it")
                _firstYN.value = it
            }

            loadAccessTokenUseCase().let {
                Log.e("aa12", "loadAccessTokenUseCase : $it")
                _loginYN.value = it.isNotEmpty()
            }

            Log.e("aa12", "_checkYN CHANGE")
            _checkYN.value = true
        }
    }

    fun saveFirstYN(firstYN: Boolean) {
        viewModelScope.launch {
            Log.e("aa12", "saveFirstYN")
            saveFirstUseCase(firstYN)
        }
    }

}