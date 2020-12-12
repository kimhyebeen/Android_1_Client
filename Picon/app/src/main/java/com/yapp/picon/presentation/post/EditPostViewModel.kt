package com.yapp.picon.presentation.post

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.Emotion
import com.yapp.picon.presentation.model.Post

class EditPostViewModel: BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> get() =  _backButton

    private val _finishDialogConfirmButton = MutableLiveData<Boolean>()
    val finishDialogConfirmButton: LiveData<Boolean> get() =  _finishDialogConfirmButton

    private val _finishDialogCancelButton = MutableLiveData<Boolean>()
    val finishDialogCancelButton: LiveData<Boolean> get() =  _finishDialogCancelButton

    private val _postImageList = MutableLiveData<List<String>>()
    val postImageList: LiveData<List<String>> get() =  _postImageList

    private val _postAddress = MutableLiveData<String>()
    val postAddress: LiveData<String> get() =  _postAddress

    private val _postEmotion = MutableLiveData<Emotion>()
    val postEmotion: LiveData<Emotion> get() =  _postEmotion

    var postMemo = MutableLiveData<String>()

    init {
        initialize()
    }

    fun initialize() {
        _backButton.value = false
        _finishDialogConfirmButton.value = false
        _finishDialogCancelButton.value = false
    }

    fun setPostContents(post: Post) {
        _postImageList.value = post.imageUrls ?: listOf()
        _postAddress.value = post.address.address
        _postEmotion.value = post.emotion ?: throw Exception("EditPostViewModel - setPostContents - this post has not emotion.")
        postMemo.value = post.memo ?: ""
    }

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }

    fun clickFinishDialogConfirmButton(view: View) {
        _finishDialogConfirmButton.value?.let {
            _finishDialogConfirmButton.value = !it
        }
    }

    fun clickFinishDialogCancelButton(view: View) {
        _finishDialogCancelButton.value?.let {
            _finishDialogCancelButton.value = !it
        }
    }
}