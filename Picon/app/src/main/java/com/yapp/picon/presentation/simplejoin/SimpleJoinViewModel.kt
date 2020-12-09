package com.yapp.picon.presentation.simplejoin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.SimpleJoinUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SimpleJoinViewModel(
    private val simpleJoinUseCase: SimpleJoinUseCase
) : BaseViewModel() {

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _joinYN = MutableLiveData<Boolean>()
    val joinYN: LiveData<Boolean> get() = _joinYN

    var id = MutableLiveData<String>()
    var pw = MutableLiveData<String>()
    var pwRe = MutableLiveData<String>()
    var nic = MutableLiveData<String>()

    init {
        _joinYN.value = false
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    //todo 유효성 검사 가입 완료 후
    fun joinMembership() {
        viewModelScope.launch {
            val loginId = id.value
            if (loginId.isNullOrEmpty()) {
                showToast("아이디를 입력해주세요.")
                return@launch
            }

            val loginPw = pw.value
            if (loginPw.isNullOrEmpty()) {
                showToast("비밀번호를 입력해주세요.")
                return@launch
            }

            val loginPwRe = pwRe.value
            if (loginPwRe.isNullOrEmpty()) {
                showToast("비밀번호를 재입력해주세요.")
                return@launch
            }

            if(loginPw != loginPwRe) {
                showToast("비밀번호가 일치하지 않습니다.")
                return@launch
            }

            val loginNic = nic.value
            if (loginNic.isNullOrEmpty()) {
                showToast("닉네임을 재입력해주세요.")
                return@launch
            }

            simpleJoinUseCase(loginId, loginPw, loginNic).let {
                Log.e("aa12", "joinMembership : $it")
                if (it.status == 200) {
                    showToast("회원가입이 완료되었습니다!")
                    _joinYN.value = true
                } else {
                    showToast(it.errorMessage)
                }
            }
        }
    }

}