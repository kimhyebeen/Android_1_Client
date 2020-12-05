package com.yapp.picon.presentation.nav.manageFriend

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.domain.usecase.LoadAccessTokenUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.FollowItem
import kotlinx.coroutines.launch

class ManageFriendViewModel: BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> get() =  _backButton

    private val _followingList = MutableLiveData<List<FollowItem>>()
    val followingList: LiveData<List<FollowItem>> get() = _followingList

    private val _followerList = MutableLiveData<List<FollowItem>>()
    val followerList: LiveData<List<FollowItem>> get() = _followerList

    private val _searchList = MutableLiveData<List<FollowItem>>()
    val searchList: LiveData<List<FollowItem>> get() = _searchList

    val searchText = MutableLiveData<String>()

    init {
        _backButton.value = false
        searchText.value = ""

        // todo - api 연결 후 followingList, followerList 삭제
        _followingList.value = listOf(
            FollowItem(0,
                "https://images.mypetlife.co.kr/content/uploads/2019/08/23154822/dog-tongue-2.jpg",
                "apple@naver.com",
                "apple",
                true,
                true
            ),
            FollowItem(1,
                "",
                "orange@naver.com",
                "orange",
                true,
                false
            ),
            FollowItem(2,
                "https://lh3.googleusercontent.com/proxy/opjZby-CbBpFspFeRda7_1IiENwbi82BCTVKkWDswYTU4ngBL2ay8KZ2pZIWM--ZpVHzWmg6zFv6xRWWHU23pBJAstOXkcWXhoOfJ_6d3MZEvfu7Lw",
                "mango11@naver.com",
                "mango",
                true,
                true
            )
        )

        _followerList.value = listOf(
            FollowItem(0,
                "https://images.mypetlife.co.kr/content/uploads/2019/08/23154822/dog-tongue-2.jpg",
                "apple@naver.com",
                "apple",
                true,
                true
            ),
            FollowItem(1,
                "https://lh3.googleusercontent.com/proxy/opjZby-CbBpFspFeRda7_1IiENwbi82BCTVKkWDswYTU4ngBL2ay8KZ2pZIWM--ZpVHzWmg6zFv6xRWWHU23pBJAstOXkcWXhoOfJ_6d3MZEvfu7Lw",
                "mango11@naver.com",
                "mange",
                true,
                true
            ),
            FollowItem(0,
                "",
                "watermelon@naver.com",
                "watermelon",
                false,
                true
            )
        )
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

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }

    fun clickSearchDeleteButton(view: View) {
        searchText.value = ""
    }
}