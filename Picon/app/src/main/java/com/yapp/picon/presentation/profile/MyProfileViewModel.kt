package com.yapp.picon.presentation.profile

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.Address
import com.yapp.picon.presentation.model.Coordinate
import com.yapp.picon.presentation.model.Emotion
import com.yapp.picon.presentation.model.Post
import kotlinx.coroutines.launch

class MyProfileViewModel: BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    private val _profileImageUrl = MutableLiveData<String>()
    private val _postList = MutableLiveData<List<Post>>()
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
    val profileImageUrl: LiveData<String> = _profileImageUrl
    val postList: LiveData<List<Post>> = _postList
    val changeProfileImageButton: LiveData<Boolean> = _changeProfileImageButton
    val myProfileTitle: LiveData<String> = _myProfileTitle
    val following: LiveData<Int> = _following
    val follower: LiveData<Int> = _follower

    fun initFlags() {
        _backButton.value = false
        _profileImageUrl.value = ""
        _changeProfileImageButton.value = false
    }

    fun requestUserInfo(token: String) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestUserInfo(token).let {
                    _myProfileTitle.value = it.member.nickName
                    _profileImageUrl.value = it.member.profileImageUrl ?: ""
                }
            } catch (e: Exception) {
                Log.e("MyProfileViewModel", "requestUserInfo Error - ${e.message}")
            }
        }
    }

    fun requestPosts(token: String) {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestPosts(token).let { post ->
                    post.posts.map {
                        Post(it.id,
                            Coordinate(it.coordinate.lat, it.coordinate.lng),
                            it.imageUrls,
                            Address(it.address.address, it.address.addrCity, it.address.addrDo, it.address.addrGu),
                            getEmotion(it.emotion!!.name),
                            it.memo,
                            it.createdDate
                        )
                    }.let { _postList.value = it }
                }
            } catch (e: Exception) {
                Log.e("MyProfileViewModel", "requestPosts Error - ${e.message}")
            }
        }
    }

    private fun getEmotion(value: String): Emotion {
        return when (value) {
            "SOFT_BLUE" -> Emotion.SOFT_BLUE
            "CORN_FLOWER" -> Emotion.CORN_FLOWER
            "BLUE_GRAY" -> Emotion.BLUE_GRAY
            "VERY_LIGHT_BROWN" -> Emotion.VERY_LIGHT_BROWN
            "WARM_GRAY" -> Emotion.WARM_GRAY
            else -> throw Exception("MyProfileViewModel - getEmotion - color type is wrong.")
        }
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

    fun setFollowing(value: Int) {
        _following.value = value
    }

    fun setFollower(value: Int) {
        _follower.value = value
    }
}