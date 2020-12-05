package com.yapp.picon.presentation.nav.manageFriend

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.FollowItem
import kotlinx.coroutines.launch

class ManageFriendViewModel: BaseViewModel() {
    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> get() =  _toast

    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> get() =  _backButton

    private val _followingList = MutableLiveData<List<FollowItem>>()
    val followingList: LiveData<List<FollowItem>> get() = _followingList

    private val _followerList = MutableLiveData<List<FollowItem>>()
    val followerList: LiveData<List<FollowItem>> get() = _followerList

    private val _searchList = MutableLiveData<List<FollowItem>>()
    val searchList: LiveData<List<FollowItem>> get() = _searchList

    val searchText = MutableLiveData<String>()

    var token = ""

    init {
        _backButton.value = false
        searchText.value = ""
    }

    fun requestSearch(token: String, input: String) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestAllUser(token, input).members.let {  list ->
                    list.map {
                        FollowItem(
                            it.id,
                            it.profileImageUrl ?: "",
                            it.identity,
                            it.nickName,
                            false,
                            false
                        )
                    }.let { _searchList.value = it }
                }
            } catch (e: Exception) {
                Log.e("ManageFriendViewModel", "requestSearch error - ${e.message}")
            }
        }
    }

    fun requestFollowingList(token: String) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestFollowingList(token).members.let { list ->
                    list.map {
                        FollowItem(
                            it.id,
                            it.profileImageUrl ?: "",
                            it.identity,
                            it.nickName,
                            it.isFollowing ?: false,
                            false
                        )
                    }.let { _followingList.value = it }
                }
            } catch (e: Exception) {
                Log.e("ManageFriendViewModel", "requestFollowingList error - ${e.message}")
            }
        }
    }

    fun requestFollowerList(token: String) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestFollowerList(token).members.let { list ->
                    list.map {
                        FollowItem(
                            it.id,
                            it.profileImageUrl ?: "",
                            it.identity,
                            it.nickName,
                            it.isFollowing ?: false,
                            true
                        )
                    }.let { _followerList.value = it }
                }
            } catch (e: Exception) {
                Log.e("ManageFriendViewModel", "requestFollowerList error - ${e.message}")
            }
        }
    }

    fun requestFollow(token: String, id: Int) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestFollow(token, id)
            } catch (e: Exception) {
                Log.e("ManageFriendViewModel", "requestFollow error - ${e.message}")
            }
        }
    }

    fun requestUnFollow(token: String, id: Int) {
        // todo
    }

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }

    fun clickSearchDeleteButton(view: View) {
        searchText.value = ""
    }

    fun showToast(str: String) {
        _toast.value = str
    }
}