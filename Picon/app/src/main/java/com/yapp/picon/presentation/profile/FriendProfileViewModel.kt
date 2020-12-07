package com.yapp.picon.presentation.profile

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.model.Members
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.domain.usecase.LoadAccessTokenUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class FriendProfileViewModel(
    private val loadAccessTokenUseCase: LoadAccessTokenUseCase
): BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> get() =  _backButton

    private val _member = MutableLiveData<Members>()
    val member: LiveData<Members> get() =  _member

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() =  _nickname

    private val _image = MutableLiveData<String>()
    val image: LiveData<String> get() =  _image

    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean> get() =  _isFollowing

    private val _following = MutableLiveData<Int>()
    val following: LiveData<Int> get() =  _following

    private val _follower = MutableLiveData<Int>()
    val follower: LiveData<Int> get() =  _follower

    init {
        // todo - following, follower 수 받아오기
        _following.value = 0
        _follower.value = 0

        _backButton.value = false
    }

    fun requestFriendProfile(input: String) {
        viewModelScope.launch {
            try {
                loadAccessTokenUseCase().let { token ->
                    if (token.isNotEmpty()) {
                        NetworkModule.yappApi.requestAllUser(token, input).let {
                            it.members[0].run {
                                _member.value = this
                                _nickname.value = this.nickName
                                _image.value = this.profileImageUrl ?: ""
                                _isFollowing.value = this.isFollowing ?: false
                            }
                        }
                    } else Log.e("FriendProfileViewModel","requestFriendProfile - token is empty")
                }
            } catch (e: Exception) {
                Log.e("FriendProfileViewModel","requestFriendProfile error - ${e.message}")
            }
        }
    }

    fun requestFollow() {
        viewModelScope.launch {
            try {
                loadAccessTokenUseCase().let { token ->
                    if (token.isNotEmpty()) {
                        _member.value?.let {
                            NetworkModule.yappApi.requestFollow(token, it.id)
                            _isFollowing.value = true
                        }
                    } else Log.e("FriendProfileViewModel","requestFollow - token is empty")
                }
            } catch (e: Exception) {
                Log.e("FriendProfileViewModel","requestFollow error - ${e.message}")
            }
        }
    }

    fun requestUnFollow() {
        viewModelScope.launch {
            try {
                loadAccessTokenUseCase().let { token ->
                    if (token.isNotEmpty()) {
                        _member.value?.let {
                            NetworkModule.yappApi.requestUnFollow(token, it.id)
                            _isFollowing.value = false
                        }
                    } else Log.e("FriendProfileViewModel","requestUnFollow - token is empty")
                }
            } catch (e: Exception) {
                Log.e("FriendProfileViewModel","requestUnFollow error - ${e.message}")
            }
        }
    }

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }
}