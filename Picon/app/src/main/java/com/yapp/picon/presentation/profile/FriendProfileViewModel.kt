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
import com.yapp.picon.presentation.model.Address
import com.yapp.picon.presentation.model.Coordinate
import com.yapp.picon.presentation.model.Emotion
import com.yapp.picon.presentation.model.Post
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

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> get() = _postList

    init {
        // todo - following, follower 수 받아오기
        _following.value = 0
        _follower.value = 0

        _backButton.value = false
        _postList.value = listOf()
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
                        } ?: Log.e("FriendProfileViewModel", "requestFollow - member is null")
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
                        } ?: Log.e("FriendProfileViewModel", "requestUnFollow - member is null")
                    } else Log.e("FriendProfileViewModel","requestUnFollow - token is empty")
                }
            } catch (e: Exception) {
                Log.e("FriendProfileViewModel","requestUnFollow error - ${e.message}")
            }
        }
    }

    fun requestPostList(id: Int) {
        viewModelScope.launch {
            try {
                loadAccessTokenUseCase().let { token ->
                    if (token.isNotEmpty()) {
                        NetworkModule.yappApi.requestFriendPosts(token, id).posts.let { list ->
                            list.map { post ->
                                Post(
                                    post.id,
                                    Coordinate(post.coordinate.lat, post.coordinate.lng),
                                    post.imageUrls,
                                    Address(
                                        post.address.address,
                                        post.address.addrCity,
                                        post.address.addrDo,
                                        post.address.addrGu
                                    ),
                                    getEmotion(post.emotion!!.name),
                                    post.memo,
                                    post.createdDate
                                )
                            }.let { posts -> _postList.value = posts }
                        }
                    } else Log.e("FriendProfileViewModel","requestPostList - token is empty")
                }
            } catch (e: Exception) {
                Log.e("FriendProfileViewModel","requestPostList error - ${e.message}")
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
}