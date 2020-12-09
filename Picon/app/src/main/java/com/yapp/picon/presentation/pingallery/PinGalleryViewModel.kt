package com.yapp.picon.presentation.pingallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.GetRevGeoUseCase
import com.yapp.picon.domain.usecase.RemovePostUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.Pin
import com.yapp.picon.presentation.model.Post
import kotlinx.coroutines.launch

class PinGalleryViewModel(
    private val getRevGeoUseCase: GetRevGeoUseCase,
    private val removePostUseCase: RemovePostUseCase
) : BaseViewModel() {

    private val _items = MutableLiveData<List<Pin>>()
    val items: LiveData<List<Pin>> get() = _items

    private val _posts = MutableLiveData<List<Post>>()

    private val _pinCount = MutableLiveData<String>()
    val pinCount: LiveData<String> get() = _pinCount

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> get() = _address

    private val _deleteCount = MutableLiveData<String>()
    val deleteCount: LiveData<String> get() = _deleteCount

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _editYN = MutableLiveData<Boolean>()
    val editYN: LiveData<Boolean> get() = _editYN

    private val _showYN = MutableLiveData<Boolean>()
    val showYN: LiveData<Boolean> get() = _showYN

    private val _deleteYN = MutableLiveData<Boolean>()
    val deleteYN: LiveData<Boolean> get() = _deleteYN

    init {
        _items.value = mutableListOf()
        _posts.value = mutableListOf()
        _deleteCount.value = "삭제"
        _showYN.value = true
        _editYN.value = false
        _deleteYN.value = false
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    fun setAddress(coords: String) {
        viewModelScope.launch {
            getRevGeoUseCase(coords).let {
                if (it.status.code == 0) {
                    val area1 = it.results[0].region.area1.name
                    val area2 = it.results[0].region.area2.name
                    val addressText = "$area1 $area2"
                    _address.value = addressText
                } else {
                    _address.value = "알수없음"
                }
            }
        }
    }

    private fun setPinCount() {
        _items.value?.let {
            _pinCount.value = "${it.size} pins"
        }
    }

    fun setPosts(posts: List<Post>) {
        _posts.value = posts
        _posts.value?.let {
            _items.value = it.map { post ->
                val size = post.imageUrls?.size ?: 0
                val showManyYN = size > 1

                Pin(
                    post.id,
                    post.imageUrls?.get(0),
                    post.emotion?.rgb,
                    showManyYN = showManyYN,
                    showCbYN = false,
                    checkYN = false
                )
            }
            setPinCount()
        }
    }

    fun getPost(id: Int): Post? {
        return _posts.value?.first {
            it.id == id
        }
    }

    fun setShowMode() {
        _showYN.value = true
        _editYN.value = false
        _items.value = _items.value?.apply {
            for (item in this) {
                item.showCbYN = false
                item.checkYN = false
            }
        }
    }

    fun setEditMode() {
        _showYN.value = false
        _editYN.value = true
        _items.value = _items.value?.apply {
            for (item in this) {
                item.showCbYN = true
            }
        }
    }

    fun selectItem() {
        _items.value?.run {
            _deleteCount.value = "삭제(${count { it.checkYN }})"
        }
    }

    fun deletePost() {
        viewModelScope.launch {
            _items.value?.let { list ->

                val deletingPinCount = list.count { it.checkYN }
                var deletedPinCount = 0

                val keys = list.filter { it.checkYN }.groupBy { it.id }.keys

                keys.forEach {
                    it?.let { id ->
                        removePostUseCase(id).let { response ->
                            if (response.status == 200) {
                                deletedPinCount++
                            }
                        }
                    } ?: showToast("선택한 핀이 존재하지 않습니다.")
                }.let {
                    when {
                        (deletingPinCount == 0) and (deletedPinCount == 0) -> {
                            showToast("선택한 핀이 존재하지 않습니다.")
                        }
                        deletedPinCount == deletingPinCount -> {
                            showToast("$deletedPinCount 개의 핀이 삭제되었습니다.")
                            setShowMode()
                            _deleteYN.value = true
                        }
                        else -> {
                            showToast("삭제 중 오류가 발생했습니다.")
                            setShowMode()
                        }
                    }
                }

                val items = arrayListOf<Pin>()
                list.filter { it.id !in keys }.forEach { item -> items.add(item) }
                _items.value = items
                setPinCount()
            }
        }
    }

}