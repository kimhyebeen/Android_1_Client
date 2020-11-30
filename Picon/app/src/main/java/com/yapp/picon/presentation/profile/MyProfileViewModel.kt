package com.yapp.picon.presentation.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel

class MyProfileViewModel: BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    private val _changeProfileImageButton = MutableLiveData<Boolean>()
    private val _myProfileTitle = MutableLiveData<String>()
    private val _following = MutableLiveData<Int>()
    private val _follower = MutableLiveData<Int>()

    init {
        initFlags()

        _myProfileTitle.value = "닉네임"
        _following.value = 0
        _follower.value = 0
    }

    val backButton: LiveData<Boolean> = _backButton
    val changeProfileImageButton: LiveData<Boolean> = _changeProfileImageButton
    val myProfileTitle: LiveData<String> = _myProfileTitle
    val following: LiveData<Int> = _following
    val follower: LiveData<Int> = _follower

    fun initFlags() {
        _backButton.value = false
        _changeProfileImageButton.value = false
    }

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }

    fun clickChangeProfileImageButton(view: View) {
        _changeProfileImageButton.value?.let {
            _changeProfileImageButton.value = !it
        }
    }

    fun setTitle(value: String) {
        _myProfileTitle.value = value
    }

    fun setFollowing(value: Int) {
        _following.value = value
    }

    fun setFollower(value: Int) {
        _follower.value = value
    }
}