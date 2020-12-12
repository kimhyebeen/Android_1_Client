package com.yapp.picon.presentation.post

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.domain.usecase.LoadAccessTokenUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.Emotion
import com.yapp.picon.presentation.model.Post
import kotlinx.coroutines.launch

class EditPostViewModel(
    private val loadAccessTokenUseCase: LoadAccessTokenUseCase
): BaseViewModel() {
    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> get() =  _backButton

    private val _removePinButton = MutableLiveData<Boolean>()
    val removePinButton: LiveData<Boolean> get() =  _removePinButton

    private val _finishDialogConfirmButton = MutableLiveData<Boolean>()
    val finishDialogConfirmButton: LiveData<Boolean> get() =  _finishDialogConfirmButton

    private val _finishDialogCancelButton = MutableLiveData<Boolean>()
    val finishDialogCancelButton: LiveData<Boolean> get() =  _finishDialogCancelButton

    private val _removeDialogDeleteButton = MutableLiveData<Boolean>()
    val removeDialogDeleteButton: LiveData<Boolean> get() =  _removeDialogDeleteButton

    private val _removeDialogCancelButton = MutableLiveData<Boolean>()
    val removeDialogCancelButton: LiveData<Boolean> get() =  _removeDialogCancelButton

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
        _removePinButton.value = false
        _finishDialogConfirmButton.value = false
        _finishDialogCancelButton.value = false
        _removeDialogDeleteButton.value = false
        _removeDialogCancelButton.value = false
    }

    fun setPostContents(post: Post) {
        _postImageList.value = post.imageUrls ?: listOf()
        _postAddress.value = post.address.address
        _postEmotion.value = post.emotion ?: throw Exception("EditPostViewModel - setPostContents - this post has not emotion.")
        postMemo.value = post.memo ?: ""
    }

    fun removePost(id: Int) {
        viewModelScope.launch {
            try {
                loadAccessTokenUseCase().let { token ->
                    if (token.isNotEmpty()) {
                        NetworkModule.yappApi.removePost(token, id)
                    }
                }
            } catch (e: Exception) {
                Log.e("EditPostViewModel", "removePost error - ${e.message}")
            }
        }
    }

    fun clickBackButton(view: View) {
        _backButton.value?.let {
            _backButton.value = !it
        }
    }

    fun clickRemovePinButton(view: View) {
        _removePinButton.value?.let {
            _removePinButton.value = !it
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

    fun clickRemoveDialogDeleteButton(view: View) {
        _removeDialogDeleteButton.value?.let {
            _removeDialogDeleteButton.value = !it
        }
    }

    fun clickRemoveDialogCancelButton(view: View) {
        _removeDialogCancelButton.value?.let {
            _removeDialogCancelButton.value = !it
        }
    }
}