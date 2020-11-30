package com.yapp.picon.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.LoadAccessTokenUseCase
import com.yapp.picon.domain.usecase.LoginUseCase
import com.yapp.picon.domain.usecase.SaveAccessTokenUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val loadAccessTokenUseCase: LoadAccessTokenUseCase
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

    fun checkLogin() {
        viewModelScope.launch {
            loadAccessTokenUseCase().let {
                if (it.isNotEmpty()) {
                    _loginYN.value = true
                    Log.e("aa12", "자동으로 로그인이 됩니다.")
                }
            }
        }
    }

}