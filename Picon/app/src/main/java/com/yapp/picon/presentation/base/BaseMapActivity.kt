package com.yapp.picon.presentation.base

import android.os.Bundle
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.yapp.picon.SecretKeySet
import com.yapp.picon.databinding.ActivityMainBinding

abstract class BaseMapActivity(
    activityId: Int,
    private val mapFrameId: Int
): BaseActivity<ActivityMainBinding>(activityId), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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