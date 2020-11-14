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
            id.value?.let { id ->
                pw.value?.let { pw ->
                    pwRe.value?.let {
                        nic.value?.let { nic ->
                            simpleJoinUseCase(id, pw, nic).let {
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
            }
        }
    }
}