package com.yapp.picon.presentation.post

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yapp.picon.data.model.Coordinate
import com.yapp.picon.data.model.Emotion
import com.yapp.picon.domain.usecase.CreatePostUseCase
import com.yapp.picon.domain.usecase.UploadImageUseCase
import com.yapp.picon.presentation.base.BaseViewModel
import com.yapp.picon.presentation.model.EmotionEntity
import com.yapp.picon.presentation.util.AddressUtil
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.math.BigDecimal


class PostViewModel(
    private val uploadImageUseCase: UploadImageUseCase,
    private val createPostUseCase: CreatePostUseCase
) : BaseViewModel() {

    private val tag = "PostViewModel"

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _pictureUris = MutableLiveData<List<Uri>>()
    val pictureUris: LiveData<List<Uri>> get() = _pictureUris

    private val _emotions = MutableLiveData<List<Map<String, String>>>()
    val emotions: LiveData<List<Map<String, String>>> get() = _emotions

    private val _lat = MutableLiveData<Double>()
    private val _lng = MutableLiveData<Double>()
    private val _contentResolver = MutableLiveData<ContentResolver>()

    var memo = MutableLiveData<String>()
    var address = MutableLiveData<String>()

    private val _finishYN = MutableLiveData<Boolean>()
    val finishYN: LiveData<Boolean> get() = _finishYN

    private val _clickEmotionNumber = MutableLiveData<Int>()
    val clickEmotionNumber: LiveData<Int> get() = _clickEmotionNumber

    init {
        _pictureUris.value = mutableListOf()
        _emotions.value = mutableListOf()
        _finishYN.value = false
        _clickEmotionNumber.value = 0
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

    fun setContentResolver(contentResolver: ContentResolver) {
        _contentResolver.value = contentResolver
    }

    fun deletePictureUri(uri: Uri) {
        _pictureUris.value = _pictureUris.value?.minus(uri)
    }

    private fun getPath(uri: Uri): String? {
        _contentResolver.value?.let {
            it.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)?.run {
                val columnIndex = getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                moveToFirst()
                val str = getString(columnIndex)
                close()
                return str
            }
            return null
        }
        return null
    }

    private fun uploadImage() {
        viewModelScope.launch {
            _pictureUris.value?.let {
                val arrBody: MutableList<MultipartBody.Part> = ArrayList()

                for (uri in it) {
                    getPath(uri)?.let { strUri ->
                        File(strUri).run {

                            val mapRequestBody = LinkedHashMap<String, RequestBody>()
                            val requestBody =
                                this.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                            mapRequestBody["images\"; filename=\"$name"] = requestBody
                            val body = MultipartBody.Part.createFormData(
                                "images",
                                name,
                                requestBody
                            )

                            arrBody.add(body)
                        }
                    }
                }
                Log.e(tag, "이미지 전송시작")
                uploadImageUseCase(arrBody).let { response ->
                    Log.e(tag, "이미지 전송완료")
                    Log.e(tag, "$response")
                    createPost(response)
                }
                Log.e(tag, "이미지 전송완료 2")
            }
        }
    }

    private fun getEmotion() : Emotion? {
        return _clickEmotionNumber.value?.let {
            when(it) {
                1 -> Emotion.SOFT_BLUE
                2 -> Emotion.CORN_FLOWER
                3 -> Emotion.BLUE_GRAY
                4 -> Emotion.VERY_LIGHT_BROWN
                5 -> Emotion.WARM_GRAY
                else -> null
            }
        }
    }

    private fun createPost(imageUrls: List<String>?) {
        viewModelScope.launch {
            val lat = _lat.value ?: return@launch
            val lng = _lng.value ?: return@launch

            createPostUseCase(
                Coordinate(
                    BigDecimal(lat),
                    BigDecimal(lng)
                ),
                imageUrls,
                address = AddressUtil.getAddress(address.value.toString()),
                memo = memo.value,
                emotion = getEmotion()
            ).let {
                if (it.status == 200) {
                    showToast("새로운 핀이 작성되었습니다.")
                    _finishYN.value = true
                } else {
                    showToast("핀 작성 중 오류가 발생했습니다.")
                }
            }
        }
    }

    fun setEmotions(list: List<EmotionEntity>) {
        _emotions.value = list.map {
            mapOf(
                "color" to it.color,
                "text" to it.emotion
            )
        }
    }

    fun setClickEmotionNumber(clickEmotionNumber: Int) {
        _clickEmotionNumber.value = clickEmotionNumber
    }

    fun startCreatePost() {
        val emotion = getEmotion()
        if (emotion == null) {
            showToast("감정을 선택해주세요.")
            return
        }

        if (memo.value == null) {
            memo.value = ""
        }

        uploadImage()
    }

}