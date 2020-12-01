package com.yapp.picon.presentation.postdetail

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yapp.picon.R
import com.yapp.picon.databinding.PostDetailImageSlideFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class PostDetailImageSlideFragment(
    private val image: String
): BaseFragment<PostDetailImageSlideFragmentBinding, PostDetailViewModel>(
    R.layout.post_detail_image_slide_fragment
) {
    @Suppress("UNCHECKED_CAST")
    override val vm: PostDetailViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                PostDetailViewModel() as T
        }
    }

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
    }
}