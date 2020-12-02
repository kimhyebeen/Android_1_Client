package com.yapp.picon.presentation.postdetail

import com.bumptech.glide.Glide
import com.yapp.picon.R
import com.yapp.picon.databinding.PostDetailImageSlideFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailImageSlideFragment(
    private val image: String,
    private val clickEvent: (String) -> Unit
): BaseFragment<PostDetailImageSlideFragmentBinding, PostDetailViewModel>(
    R.layout.post_detail_image_slide_fragment
) {
    @Suppress("UNCHECKED_CAST")
    override val vm: PostDetailViewModel by viewModel()

    override fun initBinding() {
    }

    override fun onStart() {
        super.onStart()

        context?.let {
            Glide.with(it)
                .load(image)
                .centerCrop()
                .into(binding.postDetailImageSlideImage)
        }

        binding.postDetailImageSlideImage.setOnClickListener {
            clickEvent(image)
        }
    }
}