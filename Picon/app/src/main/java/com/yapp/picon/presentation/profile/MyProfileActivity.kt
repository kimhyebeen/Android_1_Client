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
    ) {
        // todo - 서버에 프로필 사진 저장
    }

    private val userVM: UserInfoViewModel by viewModel()
    override val vm: MyProfileViewModel by viewModel()

    override fun initViewModel() {
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
        vm.profileImageUrl.observe(this, {
            if (it.isEmpty()) setProfileImage(null)
            else {
                setProfileImage(it)
                // todo - 그냥 http String일 때랑, Uri로 변환해줘야 하는 걸 어떻게 구분해야하지?
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

    private fun <T> setProfileImage(value: T?) {
        value?.let {
            Glide.with(this)
                .load(it)
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