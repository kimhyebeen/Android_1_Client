package com.yapp.picon.presentation.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.domain.usecase.GetRevGeoUseCase
import com.yapp.picon.domain.usecase.RequestPostsUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.PostMarker
import com.yapp.picon.presentation.util.toPostMarker
import com.yapp.picon.presentation.util.toPresentation
import kotlinx.coroutines.launch

class MapViewModel(
    private val requestPostsUseCase: RequestPostsUseCase,
    private val getRevGeoUseCase: GetRevGeoUseCase
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

    private val _showAddressYN = MutableLiveData<Boolean>()
    val showAddressYN: LiveData<Boolean> get() = _showAddressYN

    var address = MutableLiveData<String>()

    init {
        _showBtnYN.value = false
        _showPinYN.value = false
        _postMarkers.value = mutableListOf()
        _postLoadYN.value = false
        _showAddressYN.value = false
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
                        toPostMarker(toPresentation(post))
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

    fun setAddress(lat: Double, lng: Double) {
        viewModelScope.launch {
            val coords = "$lng,$lat"

            try {
                getRevGeoUseCase(coords).let {
                    // RevGeoResult 객체 사용 (RevGeoResult 객체가 반환됩니다.)
                    Log.e(tag, it.toString())

                    if (it.status.code == 0) {
                        val area1 = it.results[0].region.area1.name
                        val area2 = it.results[0].region.area2.name
                        val area3 = it.results[0].region.area3.name
                        val area4 = it.results[0].region.area4.name
                        val addressText = "$area1 $area2 $area3 $area4"
                        _showAddressYN.value = true
                        address.value = addressText
                    } else {
                        _showAddressYN.value = false
                        address.value = ""
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, "Reverse Geo Error - ${e.message}")
                _showAddressYN.value = false
                address.value = ""
            }
        }
    }
}