package com.yapp.picon.presentation.nav

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.yapp.picon.R
import com.yapp.picon.databinding.NavActivityBinding
import com.yapp.picon.presentation.base.BaseActivity

class NavActivity : BaseActivity<NavActivityBinding, NavViewModel>(
    R.layout.nav_activity
) {

    override val vm: NavViewModel by viewModels()
    private val transaction = supportFragmentManager.beginTransaction()
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = intent.getStringExtra("type")
        getFragment()?.let {
            transaction.replace(R.id.nav_setting_frame, it).addToBackStack(null).commit()
        } ?: Log.d("NavActivity", "fragment type이 제대로 전달되지 않았습니다.")
    }

    private fun getFragment(): Fragment? {
        return when (type) {
            NavTypeStringSet.CustomEmotion.type -> CustomEmotionFragment()
            NavTypeStringSet.Setting.type -> SettingFragment()
            NavTypeStringSet.Statistic.type -> StatisticFragment()
            else -> null
        }
    }

    override fun initViewModel() {
        vm.finishFlag.observe(this) {
            if (it) {
                if (type == NavTypeStringSet.CustomEmotion.type) {
                    // TODO("다이얼로그 띄우기. 취소하면 finish 안하고, 확인하면 finish.")
                    finish()
                } else finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}