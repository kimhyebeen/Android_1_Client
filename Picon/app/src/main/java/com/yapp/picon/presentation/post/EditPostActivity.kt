package com.yapp.picon.presentation.post

import com.yapp.picon.R
import com.yapp.picon.databinding.EditPostActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPostActivity: BaseActivity<EditPostActivityBinding, EditPostViewModel>(
    R.layout.edit_post_activity
) {
    override val vm: EditPostViewModel by viewModel()

    override fun initViewModel() {
    }
}