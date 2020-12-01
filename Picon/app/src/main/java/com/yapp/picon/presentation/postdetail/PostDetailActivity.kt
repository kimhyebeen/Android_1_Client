package com.yapp.picon.presentation.postdetail

import android.os.Bundle
import android.view.View
import com.yapp.picon.R
import com.yapp.picon.data.model.Address
import com.yapp.picon.data.model.Coordinate
import com.yapp.picon.data.model.Emotion
import com.yapp.picon.data.model.Post
import com.yapp.picon.databinding.PostDetailActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal


class PostDetailActivity: BaseActivity<PostDetailActivityBinding, PostDetailViewModel>(
    R.layout.post_detail_activity
) {
    override val vm: PostDetailViewModel by viewModel()

    override fun initViewModel() {
        vm.imageList.observe(this, {
            setImagePager(it)
        })
        vm.content.observe(this, {
            if (it.isEmpty()) binding.postDetailContentText.visibility = View.GONE
            else binding.postDetailContentText.visibility = View.VISIBLE
        })
        vm.editIconFlag.observe(this, {
            if (it) {
                // todo - 버튼 리스트 띄우기
            } else {
                // todo - 버튼 리스트 감추기
            }
        })
        vm.editButtonFlag.observe(this, {
            if (it) {
                // todo - 편집 화면 띄우기
            }
        })
        vm.removeButtonFlag.observe(this, {
            if (it) {
                // todo - 게시물 지우기
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPostFromIntent()
    }

    override fun onResume() {
        super.onResume()

        vm.initFlag()
    }

    private fun getPostFromIntent() {
        // todo - intent에서 post객체 받아오기
        val exPost = Post( // 더미 데이터
            42,
            Coordinate(BigDecimal(37.5600000), BigDecimal(126.9700000)),
            listOf("https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fnaverbooking-phinf.pstatic.net%2F20201015_116%2F1602738772174qL85D_JPEG%2Fimage.jpg"),
            Address("서울 영등포구 국제금융로2길 24", "서울", "", "영등포구"),
            Emotion.VERY_LIGHT_BROWN,
            ""
        )

        setViewModel(exPost)
        setEmotionCircleImage("VERY_LIGHT_BROWN")
    }

    private fun setViewModel(post: Post) {
        vm.setImageList(post.imageUrls ?: listOf())
        vm.setAddress(post.address.address)
        vm.setDate("2020년 05월 20일")
        vm.setEmotion(Emotion.VERY_LIGHT_BROWN.toString())
        vm.setContent(post.memo ?: "")
    }

    private fun setEmotionCircleImage(color: String) {
        binding.postDetailColorCircleImage.setImageResource(
            when (color) {
                "SOFT_BLUE" -> R.drawable.ic_custom_circle_soft_blue
                "CORN_FLOWER" -> R.drawable.ic_custom_circle_cornflower
                "BLUE_GRAY" -> R.drawable.ic_custom_circle_bluegrey
                "VERY_LIGHT_BROWN" -> R.drawable.ic_custom_circle_very_light_brown
                "WARM_GRAY" -> R.drawable.ic_custom_circle_warm_grey
                else -> throw Exception("PostDetailActivity - setEmotionCircleImage - color type is wrong.")
            }
        )
    }

    private fun setImagePager(list: List<String>) {
        // todo
    }
}