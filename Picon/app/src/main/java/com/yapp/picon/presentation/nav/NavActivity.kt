package com.yapp.picon.presentation.nav

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.yapp.picon.R
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.databinding.NavActivityBinding

class NavActivity: BaseActivity<NavActivityBinding, NavViewModel>(
    R.layout.nav_activity
) {
    override val vm: NavViewModel by viewModels()
    private val transaction = supportFragmentManager.beginTransaction()
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val finishFunc = { finish() }

        fragment = intent.getStringExtra("type")
            ?.let {
                when(it) {
                    NavTypeStringSet.CustomEmotion.type -> CustomEmotionFragment(finishFunc)
                    NavTypeStringSet.Setting.type -> SettingFragment(finishFunc)
                    else -> null
                }
        }

        fragment?.let {
            transaction.replace(R.id.nav_setting_frame, it).addToBackStack(null).commit()
        } ?: Log.d("NavActivity", "fragment type이 제대로 전달되지 않았습니다.")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}