package com.yapp.picon.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.yapp.picon.R
import com.yapp.picon.databinding.SplashActivityBinding
import com.yapp.picon.presentation.base.BaseActivity
import com.yapp.picon.presentation.intro.IntroActivity
import com.yapp.picon.presentation.login.LoginActivity
import com.yapp.picon.presentation.map.MapActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashActivityBinding, SplashViewModel>(
    R.layout.splash_activity
) {

    override val vm: SplashViewModel by viewModel()

    private val splashTime = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({ vm.check() }, splashTime)
    }

    override fun initViewModel() {
        val checkYNObserver = Observer<Boolean> { checkYN ->
            if (checkYN) {
                vm.loginYN.value?.let { loginYN ->
                    if (loginYN) {
                        startMapActivity()
                    } else {
                        vm.firstYN.value?.let { firstYN ->
                            if (firstYN) {
                                vm.saveFirstYN(false)
                                startIntroActivity()
                            } else {
                                startLoginActivity()
                            }
                        }
                    }
                }
            }
        }
        vm.checkYN.observe(this, checkYNObserver)
    }

    private fun startIntroActivity() {
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun startMapActivity() {
        startActivity(Intent(this, MapActivity::class.java))
        finish()
    }

}