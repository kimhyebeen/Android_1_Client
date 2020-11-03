package com.yapp.picon.presentation.nav

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.DialogRemoveAllDataBinding
import com.yapp.picon.databinding.NavSettingFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class SettingFragment: BaseFragment<NavSettingFragmentBinding, NavViewModel>(
    R.layout.nav_setting_fragment
) {
    lateinit var dialogBinding: DialogRemoveAllDataBinding
    lateinit var dialog: AlertDialog
    lateinit var builder: AlertDialog.Builder

    @Suppress("UNCHECKED_CAST")
    override val vm: NavViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NavViewModel() as T
        }
    }

    override fun initBinding() {
        binding.setVariable(BR.navVM, vm)
        binding.setVariable(BR.settingRepo, vm.settingRepository)
    }

    override fun onStart() {
        super.onStart()
        setReviewButton()
        setRemoveDataDialog()
    }

    private fun setReviewButton() {
        vm.settingReviewFlag.observe(this, {
            if (it) {
                // TODO("앱 배포 이후에 아래 주석을 해제하면 됩니다.")
                // startAppStore()
            }
        })
    }

    private fun startAppStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=com.yapp.picon")
            setPackage("com.android.vending")
        }
        startActivity(intent)
    }

    private fun setRemoveDataDialog() {
        dialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_remove_all_data,
            null,
            false
        )
        dialogBinding.setVariable(BR.settingRepo, vm.settingRepository)
        builder = AlertDialog.Builder(context)
        builder.setView(dialogBinding.root)
        builder.setCancelable(false)
        dialog = builder.create()

        observeRemoveAllData()
        observeDialogCancel()
        observeDialogRemove()
    }

    private fun observeRemoveAllData() {
        vm.settingRemoveAllDataFlag.observe(this, {
            if (it) {
                dialog.show()
            }
        })
    }

    private fun observeDialogCancel() {
        vm.settingDialogDismissFlag.observe(this, {
            if (it) {
                dialog.dismiss()
                vm.settingInitializeDialogFlag()
            }
        })
    }

    private fun observeDialogRemove() {
        vm.settingDialogRemoveFlag.observe(this, {
            if (it) {
                // TODO("모든 데이터 삭제 - 비동기")
                Toast.makeText(context, "모든 데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                vm.settingInitializeDialogFlag()
            }
        })
    }
}