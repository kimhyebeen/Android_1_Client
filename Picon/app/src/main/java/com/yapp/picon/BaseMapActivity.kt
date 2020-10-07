package com.yapp.picon

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.yapp.picon.databinding.ActivityMainBinding

abstract class BaseMapActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {
    private val mapFrameId = R.id.mapFrame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        setMapToActivity()

    }

    private fun setMapToActivity() {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(SecretKeySet.CLIENT_ID)

        supportFragmentManager.findFragmentById(mapFrameId) as MapFragment?
            ?: addMapToFragment()
    }

    private fun addMapToFragment() {
        run {
            MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(mapFrameId, it).commit()
            }
        }.getMapAsync(this)
    }
}