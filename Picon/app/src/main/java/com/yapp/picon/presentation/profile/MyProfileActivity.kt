package com.yapp.picon.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.MyProfileActivityBinding
import com.yapp.picon.presentation.base.BaseActivity

class MyProfileActivity: BaseActivity<MyProfileActivityBinding, MyProfileViewModel>(
    R.layout.my_profile_activity
) {
    private lateinit var postAdapter: MyProfilePostAdapter

    override val vm: MyProfileViewModel by viewModels()

    override fun initViewModel() {
        vm.backButton.observe(this, {
            if (it) onBackPressed()
        })
        vm.changeProfileImageButton.observe(this, {
            if (it) {
                // todo - 프로필 이미지 변경 기능
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.profileVM, vm)
        postAdapter = MyProfilePostAdapter(this,
            { view, id -> startPostDetailActivity(view, id)},
            R.layout.my_profile_post_item,
            BR.profilePost
        )

        binding.myProfilePostRv.apply {
            adapter = postAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
        
        // todo - setItems - 포스트 조회
        // todo - requestUserInfo - 닉네임, 팔로잉, 팔로워 얻어오기
    }

    override fun onResume() {
        super.onResume()
        vm.initFlags()
    }

    private fun startPostDetailActivity(view: View, id: Int) {
        // todo - 게시글 보여주는 액티비티 실행 (id를 인텐트로 넘겨줘요)
    }
}