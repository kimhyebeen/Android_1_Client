package com.yapp.picon.presentation.nav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.LoadAccessTokenUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val loadAccessTokenUseCase: LoadAccessTokenUseCase
): BaseViewModel() {
    private val _token = MutableLiveData<String>()

    init {
        _token.value = ""
    }

    val token: LiveData<String> = _token

    fun requestUserInfo() {
        viewModelScope.launch {
            try {
                loadAccessTokenUseCase().let {
                    if (it.isNotEmpty()) {
                        _token.value = it
                    }
                }
            } catch (e: Exception) {
                println("UserInfoViewModel requestUserInfo error - ${e.message}")
            }
        }
    }
}