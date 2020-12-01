package com.yapp.picon.presentation.pindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.Post

class PinDetailViewModel() : BaseViewModel() {

    private val _item = MutableLiveData<Map<String, String>>()
    val item: LiveData<Map<String, String>> get() = _item

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    init {
        _item.value = mutableMapOf()
    }

    fun setPost(posts: Post) {
        _post.value = posts

        _post.value?.let {
            val memoYN = it.memo?.let { memo ->
                if (memo.isEmpty()) {
                    "FALSE"
                } else {
                    "TRUE"
                }
            } ?: "FALSE"

//            _item.value = mapOf(
//                "address" to it.address.address,
//                "emotionColor" to (it.emotion?.rgb ?: ""),
//                "emotionText" to (it.emotion?.rgb ?: ""),
//                "background" to "",
//                "memo" to (it.memo ?: ""),
//                "memoYN" to (it.memo.isNullOrEmpty() ?: "true")
//            )
        }
    }

}