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
import com.yapp.picon.presentation.CustomEmotion
import com.yapp.picon.presentation.base.BaseFragment

class CustomEmotionFragment: BaseFragment<NavCustomEmotionFragmentBinding, NavViewModel>(
    R.layout.nav_custom_emotion_fragment
) {
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
        // TODO("추후 서버에서 기본 데이터를 받아 건네주는 것으로 변경")
        val items = listOf(
            CustomEmotion(R.drawable.ic_custom_circle_soft_blue, "새벽 3시"),
            CustomEmotion(R.drawable.ic_custom_circle_cornflower, "구름없는 하늘"),
            CustomEmotion(R.drawable.ic_custom_circle_bluegrey, "아침 이슬"),
            CustomEmotion(R.drawable.ic_custom_circle_very_light_brown, "창문 너머 노을"),
            CustomEmotion(R.drawable.ic_custom_circle_warm_grey, "잔잔한 밤")
        )
        binding.navEmotionRecyclerView.apply {
            adapter = CustomEmotionAdapter(items)
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}