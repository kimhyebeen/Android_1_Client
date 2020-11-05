package com.yapp.picon.presentation.nav

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.DialogCustomFinishBinding
import com.yapp.picon.databinding.NavCustomEmotionFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class CustomEmotionFragment: BaseFragment<NavCustomEmotionFragmentBinding, NavViewModel>(
    R.layout.nav_custom_emotion_fragment
) {
    private lateinit var customAdapter: CustomEmotionAdapter
    private lateinit var finishDialog: Dialog
    private lateinit var finishBuilder: AlertDialog.Builder

    @Suppress("UNCHECKED_CAST")
    override val vm: NavViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                NavViewModel() as T
        }
    }

    override fun initBinding() {
        binding.setVariable(BR.navVM, vm)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        setFinishDialog()
        observeButton()

        setRecyclerView()
    }

    @SuppressLint("InflateParams")
    private fun setFinishDialog() {
        val dialogFinishView: DialogCustomFinishBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_custom_finish, null, false
        )
        dialogFinishView.setVariable(BR.customRepo, vm.customRepository)
        finishBuilder = AlertDialog.Builder(context)
        finishBuilder.setView(dialogFinishView.root)
        finishDialog = finishBuilder.create()
    }

    private fun observeButton() {
        vm.customRepository.customFinishFlag.observe(this, {
            if (it) {
                finishDialog.show()
            }
        })

        vm.customRepository.dialogFinishCancelFlag.observe(this, {
            if (it) {
                finishDialog.dismiss()
                vm.customRepository.initFinishDialogFlag()
                vm.customRepository.changeCustomFinishFlag()
            }
        })

        vm.customRepository.dialogFinishConfirmFlag.observe(this, {
            if (it) {
                finishDialog.dismiss()
                vm.changeFinishFlag()
            }
        })

        vm.customRepository.customSaveFlag.observe(this, {
            if (it) {
                // TODO("custom repository의 items를 서버에 저장")
                Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setRecyclerView() {
        customAdapter = CustomEmotionAdapter(vm.customRepository.items.value ?: listOf()
        ) { index: Int, value: String ->
            vm.customRepository.setItems(index, value)
        }

        binding.navEmotionRecyclerView.apply {
            adapter = customAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}