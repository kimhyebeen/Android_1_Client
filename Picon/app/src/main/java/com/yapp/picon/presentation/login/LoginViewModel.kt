package com.yapp.picon.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.LoginUseCase
import com.yapp.picon.domain.usecase.SaveAccessTokenUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase
) : BaseViewModel() {

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _loginYN = MutableLiveData<Boolean>()
    val loginYN: LiveData<Boolean> get() = _loginYN

    var id = MutableLiveData<String>()
    var pw = MutableLiveData<String>()

    init {
        _loginYN.value = false
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    fun login() {
        viewModelScope.launch {
            val loginId = id.value
            if(loginId.isNullOrEmpty()) {
                showToast("아이디를 입력해주세요.")
                return@launch
            }

            val loginPw = pw.value
            if(loginPw.isNullOrEmpty()) {
                showToast("비밀번호를 입력해주세요.")
                return@launch
            }

            loginUseCase(loginId, loginPw).let {
                if (it.status == 200) {
                    saveAccessTokenUseCase(it.accessToken)
                    showToast("로그인 되었습니다.")
                    _loginYN.value = true
                } else {
                    showToast(it.errorMessage)
                }
            }
        }
    }

}