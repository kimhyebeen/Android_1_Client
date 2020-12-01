package com.yapp.picon.presentation.postdetail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yapp.picon.presentation.base.BaseViewModel

class PostDetailViewModel: BaseViewModel() {
    private val _imageList = MutableLiveData<List<String>>()
    val imageList: LiveData<List<String>> get() = _imageList

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> get() = _address

    private val _emotion = MutableLiveData<String>()
    val emotion: LiveData<String> get() = _emotion

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> get() = _content

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private val _imageNumber = MutableLiveData<String>()
    val imageNumber: LiveData<String> get() = _imageNumber

    private val _editIconFlag = MutableLiveData<Boolean>()
    val editIconFlag: LiveData<Boolean> get() = _editIconFlag

    private val _editButtonFlag = MutableLiveData<Boolean>()
    val editButtonFlag: LiveData<Boolean> get() = _editButtonFlag

    private val _removeButtonFlag = MutableLiveData<Boolean>()
    val removeButtonFlag: LiveData<Boolean> get() = _removeButtonFlag

    init {
        initFlag()
    }

    fun initFlag() {
        _editIconFlag.value = false
        _editButtonFlag.value = false
        _removeButtonFlag.value = false
    }

    fun setImageList(list: List<String>) {
        _imageList.value = list
    }

    fun setAddress(value: String) {
        _address.value = value
    }

    fun setEmotion(value: String) {
        _emotion.value = value
    }

    fun setContent(value: String) {
        _content.value = value
    }

    fun setDate(year: Int, month: Int, day: Int) {
        if (month < 10 && day < 10) {
            _date.value = "${year}년 0${month}월 0${day}일"
        } else if (month < 10 && day >= 10) {
            _date.value = "${year}년 0${month}월 ${day}일"
        } else if (month >= 10 && day < 10) {
            _date.value = "${year}년 ${month}월 0${day}일"
        } else {
            _date.value = "${year}년 ${month}월 ${day}일"
        }
    }

    fun setImageNumber(count: Int, total: Int) {
        _imageNumber.value = "$count/$total"
    }

    fun clickEditIcon(view: View) {
        _editIconFlag.value?.let {
            _editIconFlag.value = !it
        }
    }

    fun clickEditButton(view: View) {
        _editButtonFlag.value?.let {
            _editButtonFlag.value = !it
        }
    }

    fun clickRemoveButton(view: View) {
        _removeButtonFlag.value?.let {
            _removeButtonFlag.value = !it
        }
    }
}