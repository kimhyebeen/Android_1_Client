package com.yapp.picon.presentation.nav.manageFriend

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel

class ManageFriendViewModel: BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> get() =  _backButton

    val searchText = MutableLiveData<String>()

    init {
        _backButton.value = false
        searchText.value = ""
    }

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }

    fun clickSearchDeleteButton(view: View) {
        searchText.value = ""
    }
}