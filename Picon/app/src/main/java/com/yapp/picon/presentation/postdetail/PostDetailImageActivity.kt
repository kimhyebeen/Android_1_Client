package com.yapp.picon.presentation.postdetail

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.yapp.picon.R
import com.yapp.picon.databinding.PostDetailImageActivityBinding
import com.yapp.picon.presentation.base.BaseActivity

class PostDetailImageActivity: BaseActivity<PostDetailImageActivityBinding, PostDetailImageViewModel>(
    R.layout.post_detail_image_activity
) {
    override val vm: PostDetailImageViewModel by viewModels()

    override fun initViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val image = intent.getStringExtra("image")
        val color = intent.getStringExtra("color")

        Glide.with(this)
            .load(image)
            .into(binding.postDetailImageView)

        binding.postDetailImageLayout.setBackgroundResource(
            getEmotionColor(color ?: "")
        )
    }

    private fun getEmotionColor(color: String): Int {
        return when (color) {
            "SOFT_BLUE" -> R.color.soft_blue_30
            "CORN_FLOWER" -> R.color.cornflower_30
            "BLUE_GRAY" -> R.color.blue_gray_30
            "VERY_LIGHT_BROWN" -> R.color.very_light_brown_30
            "WARM_GRAY" -> R.color.warm_gray_30
            else -> throw Exception("PostDetailActivity - setBackgroundColor - color type is wrong.")
        }
    }
}