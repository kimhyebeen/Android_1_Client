package com.yapp.picon.presentation.post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.R
import com.yapp.picon.data.model.Address
import com.yapp.picon.data.model.Coordinate
import com.yapp.picon.data.model.Post
import com.yapp.picon.data.network.NetworkModule
import com.yapp.picon.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class PostViewModel : BaseViewModel() {

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _pictureUris = MutableLiveData<List<Uri>>()
    val pictureUris: LiveData<List<Uri>> get() = _pictureUris

    private val _emotions = MutableLiveData<List<Map<String, String>>>()
    val emotions: LiveData<List<Map<String, String>>> get() = _emotions

    private val _lat = MutableLiveData<Double>()
    private val _lng = MutableLiveData<Double>()
    private val _selectedEmotion = MutableLiveData<String>()

    init {
        _pictureUris.value = mutableListOf()

        //todo emotions room database로 만들고 변경하기
        _emotions.value = listOf(
            mapOf(
                "color" to R.color.soft_blue.toString(),
                "text" to "새벽 3",
                "bg" to R.drawable.ic_custom_circle_soft_blue.toString()
            ),
            mapOf(
                "color" to R.color.cornflower.toString(),
                "text" to "구름없는 하늘",
                "bg" to R.drawable.ic_custom_circle_cornflower.toString(),
            ),
            mapOf(
                "color" to R.color.bluegrey.toString(),
                "text" to "아침 이",
                "bg" to R.drawable.ic_custom_circle_bluegrey.toString(),
            ),
            mapOf(
                "color" to R.color.very_light_brown.toString(),
                "text" to "창문 너머 노",
                "bg" to R.drawable.ic_custom_circle_very_light_brown.toString(),
            ),
            mapOf(
                "color" to R.color.warm_grey.toString(),
                "text" to "잔잔한 ",
                "bg" to R.drawable.ic_custom_circle_warm_grey.toString(),
            ),
        )
    }

    private fun showToast(msg: String) {
        _toastMsg.value = msg
    }

    fun setPictureUris(pictureUris: List<Uri>) {
        _pictureUris.value = pictureUris
    }

    fun setLatLng(lat: Double, lng: Double) {
        _lat.value = lat
        _lng.value = lng
    }

    fun deletePictureUri(uri: Uri) {
        _pictureUris.value = _pictureUris.value?.minus(uri)
    }

    fun setSelectedEmotion(emtion: String) {
        _selectedEmotion.value = emtion
    }

    fun requestPost() {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.requestPost("1").let {
                    showToast("감정 : ${it.emotion} 메모 : ${it.memo}")
                }
            } catch (e: Exception) {
                showToast("통신 오류")
            }
        }
    }

    fun createPost() {
        viewModelScope.launch {
            try {
                NetworkModule.yappApi.createPost(
                    Post(
                        10,
                        Coordinate(
                            1.5,
                            1.5
                        ),
                        Address(
                            "null",
                            "null",
                            "null",
                            "null"
                        ),
                        "1",
                        "1",
                        null
                    )
                ).let {
                    showToast(it.toString())
                }
            } catch (e: Exception) {
                showToast("통신 오류")
            }
        }
    }
}