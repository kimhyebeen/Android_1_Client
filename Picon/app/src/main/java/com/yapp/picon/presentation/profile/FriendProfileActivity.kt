package com.yapp.picon.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.FriendProfileActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.model.Post
import com.yapp.picon.presentation.postdetail.PostDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendProfileActivity: BaseActivity<FriendProfileActivityBinding, FriendProfileViewModel>(
    R.layout.friend_profile_activity
) {
    private lateinit var postAdapter: MyProfilePostAdapter
    private var userId: Int? = null

    override val vm: FriendProfileViewModel by viewModel()

    override fun initViewModel() {
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
        vm.image.observe(this, {
            setProfileImage(it)
        })
        vm.isFollowing.observe(this, {
            setFollowButton(it)
        })
        vm.postList.observe(this, {
            postAdapter.setItems(it)
            postAdapter.notifyDataSetChanged()
        })
        vm.member.observe(this, {
            if (userId == null) userId = it.id
            vm.requestPostList(it.id)
        })
    }

    override fun onResume() {
        super.onResume()

        userId?.let { vm.requestPostList(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.fpVM, vm)

        postAdapter = MyProfilePostAdapter(this,
            { view, post -> startPostDetailActivity(view, post) },
            R.layout.my_profile_post_item,
            BR.profilePost
        )
        binding.friendProfilePostRv.apply {
            adapter = postAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }

        val identity = intent.getStringExtra("identity")
        vm.requestFriendProfile(identity)
    }

    private fun setProfileImage(value: String) {
        if (value.isNotEmpty()) {
            Glide.with(this)
                .load(value)
                .centerCrop()
                .circleCrop()
                .into(binding.friendProfileUserImage)
        } else {
            Glide.with(this)
                .load(R.drawable.profile_pic)
                .centerCrop()
                .circleCrop()
                .into(binding.friendProfileUserImage)
        }
    }

    private fun setFollowButton(value: Boolean) {
        binding.friendProfileFollowButton.apply {
            if (value) {
                setBackgroundResource(R.drawable.bg_un_follow_button)
                text = "언팔로우"
            } else {
                setBackgroundResource(R.drawable.bg_follow_button)
                text = "팔로우"
            }

            setOnClickListener {
                if (value) vm.requestUnFollow()
                else vm.requestFollow()
            }
        }
    }

    private fun startPostDetailActivity(view: View, post: Post) {
        Intent(this, PostDetailActivity::class.java).apply {
            putExtra("post", post)
        }.let {
            startActivity(it)
        }
    }
}