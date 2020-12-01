package com.yapp.picon.presentation.pingallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.Post

class PinGalleryViewModel() : BaseViewModel() {

    private val _items = MutableLiveData<List<Map<String, String>>>()
    val items: LiveData<List<Map<String, String>>> get() = _items

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    private val _selectedPostIds = MutableLiveData<List<Int>>()
    val selectedPostIds: LiveData<List<Int>> get() = _selectedPostIds

//
//    private val _toastMsg = MutableLiveData<String>()
//    val toastMsg: LiveData<String> get() = _toastMsg

    init {
        _items.value = mutableListOf()
        _posts.value = mutableListOf()
    }

//    private fun showToast(msg: String) {
//        _toastMsg.value = msg
//    }

    fun setPosts(posts: List<Post>) {
        _posts.value = posts
        _items.value = _posts.value?.map {
            val size = it.imageUrls?.size ?: 0
            val oneYN = if (size <= 1) {
                "true"
            } else {
                "false"
            }

            mapOf(
                "img" to (it.imageUrls?.get(0) ?: ""),
                "color" to (it.emotion?.rgb ?: ""),
                "oneYN" to oneYN,
                "selectYN" to "false"
            )
        }
    }

    fun getPost(id: Int): Post? {
        return _posts.value?.first {
            it.id == id
        }
    }

}