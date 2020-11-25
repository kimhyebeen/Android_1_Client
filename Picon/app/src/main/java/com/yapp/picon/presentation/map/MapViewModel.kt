package com.yapp.picon.presentation.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.RequestPostsUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.PostMarker
import kotlinx.coroutines.launch

class MapViewModel(
    private val requestPostsUseCase: RequestPostsUseCase
) : BaseViewModel() {
    private val tag = "MapViewModel"

    private val _showBtnYN = MutableLiveData<Boolean>()
    val showBtnYN: LiveData<Boolean> get() = _showBtnYN

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _showPinYN = MutableLiveData<Boolean>()
    val showPinYN: LiveData<Boolean> get() = _showPinYN

    private val _postMarkers = MutableLiveData<List<PostMarker>>()
    val postMarkers: LiveData<List<PostMarker>> get() = _postMarkers

    private val _postLoadYN = MutableLiveData<Boolean>()
    val postLoadYN: LiveData<Boolean> get() = _postLoadYN

    init {
        _showBtnYN.value = false
        _showPinYN.value = false
        _postMarkers.value = mutableListOf()
        _postLoadYN.value = false
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    fun toggleShowBtnYN() {
        _showBtnYN.value = _showBtnYN.value?.let {
            !it
        }
    }

    fun toggleShowPinYN() {
        _showPinYN.value = _showPinYN.value?.let {
            !it
        }
    }

    fun requestPosts() {
        viewModelScope.launch {
            requestPostsUseCase().let { postsResponse ->
                Log.e(tag, "requestPosts FINISH")
                if (postsResponse.status == 200) {
                    _postMarkers.value = postsResponse.posts.map { post ->
                        PostMarker(
                            post.id,
                            post.coordinate,
                            post.imageUrls,
                            post.address,
                            post.emotion,
                            post.memo
                        )
                    }

                    _postLoadYN.value = true

                } else {
                    showToast("Error : ${postsResponse.errorMessage}")
                }
            }
        }
    }

    fun completeLoadPost() {
        _postLoadYN.value = false
    }

}