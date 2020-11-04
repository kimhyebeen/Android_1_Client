package com.yapp.picon.presentation.nav

import android.app.AlertDialog
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.DialogCustomEmotionBinding
import com.yapp.picon.databinding.DialogRemoveAllDataBinding
import com.yapp.picon.databinding.NavCustomEmotionFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class CustomEmotionFragment: BaseFragment<NavCustomEmotionFragmentBinding, NavViewModel>(
    R.layout.nav_custom_emotion_fragment
) {
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
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        setDialog()
        // adapter에 dialog를 넘겨줘요
        binding.navEmotionRecyclerView.apply {
            adapter = CustomEmotionAdapter(dialog)
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setDialog() {
        val dialogBinding: DialogCustomEmotionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_custom_emotion,
            null,
            false
        )
        dialogBinding.setVariable(BR.customRepo, vm.customRepository)

        builder = AlertDialog.Builder(context)
        builder.setView(R.layout.dialog_custom_emotion)
        builder.setCancelable(false)
        dialog = builder.create()
    }
}