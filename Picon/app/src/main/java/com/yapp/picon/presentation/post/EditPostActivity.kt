package com.yapp.picon.presentation.post

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.DialogEditPostFinishBinding
import com.yapp.picon.databinding.EditPostActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPostActivity: BaseActivity<EditPostActivityBinding, EditPostViewModel>(
    R.layout.edit_post_activity
) {
    private lateinit var finishDialog: Dialog
    private lateinit var finishBuilder: AlertDialog.Builder

    override val vm: EditPostViewModel by viewModel()

    override fun initViewModel() {
        binding.setVariable(BR.epVM, vm)

        vm.backButton.observe(this) {
            if (it) {
                setFinishDialog()
                finishDialog.show()
                setDialogSize(finishDialog)
            }
        }

        vm.finishDialogConfirmButton.observe(this) {
            if (it) finish()
        }

        vm.finishDialogCancelButton.observe(this) {
            if (it) {
                finishDialog.dismiss()
                vm.initialize()
            }
        }
    }

    private fun setFinishDialog() {
        val dialogFinishView: DialogEditPostFinishBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.dialog_edit_post_finish, null, false
        )
        dialogFinishView.setVariable(BR.depVM, vm)
        finishBuilder = AlertDialog.Builder(this)
        finishBuilder.setView(dialogFinishView.root)

        finishDialog = finishBuilder.create()
        finishDialog.setCancelable(false)
        finishDialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun setDialogSize(dialog: Dialog) {
        val layoutParams = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window!!.attributes)
            width = (274 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.window?.attributes = layoutParams
    }
}