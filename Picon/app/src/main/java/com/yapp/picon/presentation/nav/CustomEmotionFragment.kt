package com.yapp.picon.presentation.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.picon.BR
import com.yapp.picon.R
import com.yapp.picon.databinding.NavCustomEmotionFragmentBinding
import com.yapp.picon.presentation.base.BaseFragment

class CustomEmotionFragment: BaseFragment<NavCustomEmotionFragmentBinding, NavViewModel>(
    R.layout.nav_custom_emotion_fragment
) {
    private lateinit var customAdapter: CustomEmotionAdapter

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