package com.yapp.picon.presentation.post

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.define.Define.ALBUM_REQUEST_CODE
import com.sangcomz.fishbun.define.Define.INTENT_PATH
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.PostActivityBinding
import com.yapp.picon.databinding.PostEmotionItemBinding
import com.yapp.picon.databinding.PostPictureItemBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.nav.repository.EmotionDatabaseRepository
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostActivity : BaseActivity<PostActivityBinding, PostViewModel>(
    R.layout.post_activity
) {

    override val vm: PostViewModel by viewModel()

    private val postPictureClickAdapter =
        object :
            PostPictureClickAdapter<PostPictureItemBinding>(
                R.layout.post_picture_item,
                BR.postPictureItem,
                { item: Uri? -> itemClicked(item) }
            ) {}

    private fun itemClicked(item: Uri?) {
        item?.let {
            vm.deletePictureUri(it)
        }
    }

    private val postEmotionClickAdapter =
        object :
            PostEmotionClickAdapter<PostEmotionItemBinding>(
                R.layout.post_emotion_item,
                BR.postEmotionItem,
                { item: Map<String, String>? -> itemClicked(item) }
            ) {}

    private fun itemClicked(item: Map<String, String>?) {
        item?.let {
            it["color"]?.let { color ->
                vm.setSelectedEmotion(color)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAdapter()
        setOnClickListeners()

        setEmotions()
        setData()
        setContentResolver()
        startAlbum()
    }

    private fun setAdapter() {
        binding.postPictureRecyclerView.adapter = postPictureClickAdapter
        binding.postEmotionRecyclerView.adapter = postEmotionClickAdapter
    }

    private fun setOnClickListeners() {
        binding.postIvBack.setOnClickListener { finish() }
        binding.postTvSave.setOnClickListener { vm.uploadImage() }
    }

    private fun setEmotions() {
        EmotionDatabaseRepository(application).getAll().observe(this, { vm.setEmotions(it) })
    }

    private fun setData() {
        intent.run {
            val lat = getDoubleExtra("lat", 0.0)
            val lng = getDoubleExtra("lng", 0.0)
            if ((lat == 0.0) or (lng == 0.0)) {
                showToast("선택한 핀의 위치가 올바르지 않습니다.")
                finish()
            }
            vm.setLatLng(lat, lng)

            val address = getStringExtra("address")
            vm.address.value = address
        }
    }

    private fun setContentResolver() {
        vm.setContentResolver(contentResolver)
    }

    private fun startAlbum() {
        FishBun.with(this@PostActivity)
            .setImageAdapter(GlideAdapter())
            .setActionBarColor(
                ResourcesCompat.getColor(resources, R.color.dark_grey, null),
                ResourcesCompat.getColor(resources, R.color.dark_grey, null),
                false
            ).setActionBarTitleColor(Color.parseColor("#ffffff"))
            .setPickerSpanCount(3)
            .isStartInAllView(true)
            .startAlbum()
    }

    override fun initViewModel() {
        binding.setVariable(BR.postVM, vm)

        vm.toastMsg.observe(this, { showToast(it) })
        vm.finishYN.observe(this, {
            if (it) {
                finish()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ALBUM_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.run {
                        getParcelableArrayListExtra<Uri>(INTENT_PATH)?.let {
                            vm.setPictureUris(it)
                        }
                    }
                }
            }
        }
    }

}