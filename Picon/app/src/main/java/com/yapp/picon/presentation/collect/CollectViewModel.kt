package com.yapp.picon.presentation.collect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.RequestPostsUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.Pin
import com.yapp.picon.presentation.model.Post
import com.yapp.picon.presentation.util.toPresentation
import kotlinx.coroutines.launch

class CollectViewModel(
    private val getPostsUseCase: RequestPostsUseCase
) : BaseViewModel() {

    private val _items = MutableLiveData<List<Pin>>()
    val items: LiveData<List<Pin>> get() = _items

    private val _posts = MutableLiveData<List<Post>>()

    private val _locationCount = MutableLiveData<String>()
    val locationCount: LiveData<String> get() = _locationCount

    private val _emotionCount = MutableLiveData<String>()
    val emotionCount: LiveData<String> get() = _emotionCount


    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    init {
        _items.value = mutableListOf()
        _posts.value = mutableListOf()
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    fun setPosts() {
        viewModelScope.launch {
            getPostsUseCase().posts.let { postList ->
                _posts.value = postList.map {
                    toPresentation(it)
                }

                _items.value = _posts.value?.let { posts ->
                    _locationCount.value = "위치(${posts.groupBy { it.address.addrGu }.keys.size})"
                    _emotionCount.value = "감정(${posts.groupBy { it.emotion }.keys.size})"

                    posts.map {
                        val size = it.imageUrls?.size ?: 0
                        val showManyYN = size <= 1

                        Pin(
                            it.id,
                            it.imageUrls?.get(0),
                            it.emotion?.rgb,
                            showManyYN = showManyYN,
                            showCbYN = false,
                            checkYN = false
                        )
                    }
                }
            }
        }
    }

    fun getPost(id: Int): Post? {
        return _posts.value?.first {
            it.id == id
        }
    }

}