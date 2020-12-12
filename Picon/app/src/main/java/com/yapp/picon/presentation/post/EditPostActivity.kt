package com.yapp.picon.presentation.post

import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.EditPostActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPostActivity: BaseActivity<EditPostActivityBinding, EditPostViewModel>(
    R.layout.edit_post_activity
) {
    override val vm: EditPostViewModel by viewModel()

    override fun initViewModel() {
        binding.setVariable(BR.epVM, vm)

        vm.backButton.observe(this) {
            if (it) {
                // todo - dialog 띄우기
            }
        }
    }

    override fun onResume() {
        super.onResume()

        vm.initialize()
    }
}