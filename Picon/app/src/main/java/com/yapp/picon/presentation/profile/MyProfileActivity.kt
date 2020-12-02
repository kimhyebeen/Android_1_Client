package com.yapp.picon.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.MyProfileActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.model.Post
import com.yapp.picon.presentation.nav.UserInfoViewModel
import com.yapp.picon.presentation.postdetail.PostDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyProfileActivity: BaseActivity<MyProfileActivityBinding, MyProfileViewModel>(
    R.layout.my_profile_activity
) {
    private lateinit var postAdapter: MyProfilePostAdapter
    private val getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageUri ->
        // todo - 여기서 갤러리에서 선택한 이미지 uri를 받아서, 프로필 이미지를 업데이터 해야합니다.
    }

    private val userVM: UserInfoViewModel by viewModel()
    override val vm: MyProfileViewModel by viewModel()

    override fun initViewModel() {
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
        vm.profileImageUrl.observe(this, {
            if (it.isNotEmpty()) setProfileImage(it)
            else {
                setProfileImage(null)
            }
        })
        vm.changeProfileImageButton.observe(this, {
            if (it) {
                getContent.launch("image/*")
            }
        })
        vm.postList.observe(this, {
            postAdapter.setItems(it)
            postAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.profileVM, vm)

        postAdapter = MyProfilePostAdapter(
            this,
            { view, post -> startPostDetailActivity(view, post) },
            R.layout.my_profile_post_item,
            BR.profilePost
        )

        binding.myProfilePostRv.apply {
            adapter = postAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }

        observeUserToken()
    }

    override fun onResume() {
        super.onResume()
        userVM.requestUserInfo()
        vm.initFlags()
    }

    private fun setProfileImage(value: String?) {
        value?.let {
            Glide.with(this)
                .load(value)
                .circleCrop()
                .into(binding.myProfileUserImage)
        } ?: run {
            Glide.with(this)
                .load(R.drawable.profile_pic)
                .circleCrop()
                .into(binding.myProfileUserImage)
        }
    }

    private fun observeUserToken() {
        userVM.token.observe(this, { token ->
            // todo - 팔로잉, 팔로워 얻어오기
            vm.requestPosts(token)
            vm.requestUserInfo(token)
        })
    }

    private fun startPostDetailActivity(view: View, post: Post) {
        Intent(this, PostDetailActivity::class.java).apply {
            putExtra("post", post)
        }.let {
            startActivity(it)
        }
    }
}