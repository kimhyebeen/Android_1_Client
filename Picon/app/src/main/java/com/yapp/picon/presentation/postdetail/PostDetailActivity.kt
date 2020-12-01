package com.yapp.picon.presentation.postdetail

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.PostDetailActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.model.Emotion
import com.yapp.picon.presentation.model.Post
import com.yapp.picon.presentation.nav.repository.EmotionDatabaseRepository
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailActivity: BaseActivity<PostDetailActivityBinding, PostDetailViewModel>(
    R.layout.post_detail_activity
) {
    private lateinit var imagePagerAdapter: ImagePagerAdapter
    private lateinit var emotionDatabaseRepository: EmotionDatabaseRepository
    private var totalImage = 0

    override val vm: PostDetailViewModel by viewModel()

    override fun initViewModel() {
        binding.setVariable(BR.postDetailVM, vm)

        vm.imageList.observe(this, {
            imagePagerAdapter.setItems(it)
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

        setImagePager()
        getPostFromIntent()
    }

    override fun onResume() {
        super.onResume()

        vm.initFlag()
    }

    private fun setImagePager() {
        imagePagerAdapter = ImagePagerAdapter(this)
        binding.postDetailImagePager.apply {
            adapter = imagePagerAdapter
            setOnClickListener {
                // todo - 사진 full 화면 전환
            }
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    vm.setImageNumber(position+1, totalImage)
                }
            })
        }
    }

    private fun getPostFromIntent() {
        val post = intent.getParcelableExtra<Post>("post")
        post?.let {
            setViewModel(it)
            setEmotionCircleImage(it.emotion?.name ?: "")
            setBackgroundColor(it.emotion?.name ?: "")
        }
    }

    private fun setViewModel(post: Post) {
        val dateList = post.createdDate?.split('.')
            ?: throw Exception("PostDetailActivity - setViewModel - createdDate is null")

        totalImage = post.imageUrls?.size ?: 0
        vm.setImageList(post.imageUrls ?: listOf())
        vm.setImageNumber(1, totalImage)
        vm.setAddress(post.address.address)
        vm.setDate(dateList[0].toInt(), dateList[1].toInt(), dateList[2].toInt())
        vm.setContent(post.memo ?: "")

        emotionDatabaseRepository.getAll().observe(this, { list ->
            vm.setEmotion(
                list[getColorIndex(post.emotion)].emotion
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

    private fun getColorIndex(value: Emotion?): Int {
        return when(value) {
            Emotion.SOFT_BLUE -> 0
            Emotion.CORN_FLOWER -> 1
            Emotion.BLUE_GRAY -> 2
            Emotion.VERY_LIGHT_BROWN -> 3
            Emotion.WARM_GRAY -> 4
            else -> throw Exception("PostDetailActivity - getColorIndex - emotion is null.")
        }
    }

    override fun onBackPressed() {
        binding.postDetailImagePager.currentItem.let { cur ->
            if (cur == 0) super.onBackPressed()
            else binding.postDetailImagePager.currentItem = cur - 1
        }
    }
}