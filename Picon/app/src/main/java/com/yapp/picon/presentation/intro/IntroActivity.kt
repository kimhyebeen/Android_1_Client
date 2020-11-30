package com.yapp.picon.presentation.intro

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.yapp.picon.R
import com.yapp.picon.databinding.IntroActivityBinding
import com.yapp.picon.presentation.login.LoginActivity

class IntroActivity : FragmentActivity() {

    private lateinit var binding: IntroActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.intro_activity)

        setAdapter()
    }

    private fun setAdapter() {
        binding.introViewPager.adapter = IntroFragmentAdapter(this)
        binding.introViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position >= 3) {
                    startLoginActivity()
                }
            }
        })
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

}