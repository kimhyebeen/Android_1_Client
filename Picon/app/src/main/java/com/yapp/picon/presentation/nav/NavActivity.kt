package com.yapp.picon.presentation.nav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yapp.picon.R
import com.yapp.picon.databinding.NavActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class NavActivity : BaseActivity<NavActivityBinding, NavViewModel>(
    R.layout.nav_activity
) {

    override val vm: NavViewModel by viewModel()
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
            NavTypeStringSet.CustomEmotion.type -> CustomEmotionFragment(application)
            NavTypeStringSet.Setting.type -> SettingFragment()
            NavTypeStringSet.Statistic.type -> StatisticFragment(application)
            else -> null
        }
    }

    override fun initViewModel() {
        vm.finishFlag.observe(this, {
            if (it) {
                if (type == NavTypeStringSet.CustomEmotion.type) {
                    finish()
                } else finish()
            }
        })

        vm.toastMsg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        vm.logoutYN.observe(this, {
            if (it) {
                startLoginActivity()
                finish()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun startLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }.let {
            startActivity(it)
        }
    }

}