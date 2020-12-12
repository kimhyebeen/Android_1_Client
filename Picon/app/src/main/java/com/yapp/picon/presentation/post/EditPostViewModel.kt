package com.yapp.picon.presentation.post

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel

class EditPostViewModel: BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> get() =  _backButton

    private val _finishDialogConfirmButton = MutableLiveData<Boolean>()
    val finishDialogConfirmButton: LiveData<Boolean> get() =  _finishDialogConfirmButton

    private val _finishDialogCancelButton = MutableLiveData<Boolean>()
    val finishDialogCancelButton: LiveData<Boolean> get() =  _finishDialogCancelButton

    init {
        initialize()
    }

    fun initialize() {
        _backButton.value = false
        _finishDialogConfirmButton.value = false
        _finishDialogCancelButton.value = false
    }

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }

    fun clickFinishDialogConfirmButton(view: View) {
        _finishDialogConfirmButton.value?.let {
            _finishDialogConfirmButton.value = !it
        }
    }

    fun clickFinishDialogCancelButton(view: View) {
        _finishDialogCancelButton.value?.let {
            _finishDialogCancelButton.value = !it
        }
    }
}