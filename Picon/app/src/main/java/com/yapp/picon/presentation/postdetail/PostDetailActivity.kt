package com.yapp.picon.presentation.postdetail

import android.os.Bundle
import android.view.View
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.data.model.Address
import com.yapp.picon.data.model.Coordinate
import com.yapp.picon.data.model.Emotion
import com.yapp.picon.data.model.Post
import com.yapp.picon.databinding.PostDetailActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.nav.repository.EmotionDatabaseRepository
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class PostDetailActivity: BaseActivity<PostDetailActivityBinding, PostDetailViewModel>(
    R.layout.post_detail_activity
) {
    private lateinit var emotionDatabaseRepository: EmotionDatabaseRepository
    override val vm: PostDetailViewModel by viewModel()

    override fun initViewModel() {
        binding.setVariable(BR.postDetailVM, vm)

        vm.imageList.observe(this, {
            setImagePager(it)
        })
        vm.content.observe(this, {
            if (it.isEmpty()) binding.postDetailContentText.visibility = View.GONE
            else binding.postDetailContentText.visibility = View.VISIBLE
        })
        vm.editIconFlag.observe(this, {
            if (it) {
                binding.postDetailEditLinear.visibility = View.VISIBLE
            } else {
                binding.postDetailEditLinear.visibility = View.GONE
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
        emotionDatabaseRepository = EmotionDatabaseRepository(application)

        getPostFromIntent()
        binding.postDetailImagePager.setOnClickListener {
            // todo - 사진 full 화면 전환
        }
    }

    override fun onResume() {
        super.onResume()

        vm.initFlag()
    }

    private fun setImagePager(list: List<String>) {
        // todo - 어댑터 구현 및 설정

        vm.setImageNumber(1, 3)
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
        setBackgroundColor("VERY_LIGHT_BROWN")
    }

    private fun setViewModel(post: Post) {
        // todo - post의 날짜로 적용하기
        val date = "2020.05.20"
        val dateList = date.split('.')

        vm.setImageList(post.imageUrls ?: listOf())
        vm.setAddress(post.address.address)
        vm.setDate(dateList[0].toInt(), dateList[1].toInt(), dateList[2].toInt())
        vm.setContent(post.memo ?: "")

        emotionDatabaseRepository.getAll().observe(this, { list ->
            vm.setEmotion(
                list[getColorIndex("VERY_LIGHT_BROWN")].emotion
            )
        })
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

    private fun setBackgroundColor(color: String) {
        binding.postDetailLayout.setBackgroundResource(
            when (color) {
                "SOFT_BLUE" -> R.color.soft_blue_30
                "CORN_FLOWER" -> R.color.cornflower_30
                "BLUE_GRAY" -> R.color.blue_gray_30
                "VERY_LIGHT_BROWN" -> R.color.very_light_brown_30
                "WARM_GRAY" -> R.color.warm_gray_30
                else -> throw Exception("PostDetailActivity - setBackgroundColor - color type is wrong.")
            }
        )
    }

    private fun getColorIndex(value: String): Int {
        return when(value) {
            "SOFT_BLUE" -> 0
            "CORN_FLOWER" -> 1
            "BLUE_GRAY" -> 2
            "VERY_LIGHT_BROWN" -> 3
            "WARM_GRAY" -> 4
            else -> throw Exception("PostDetailActivity - getColorIndex - color type is wrong.")
        }
    }

    override fun onBackPressed() {
        binding.postDetailImagePager.currentItem.let { cur ->
            if (cur == 0) super.onBackPressed()
            else binding.postDetailImagePager.currentItem = cur - 1
        }
    }
}