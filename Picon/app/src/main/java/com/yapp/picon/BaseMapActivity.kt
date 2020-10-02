package com.yapp.picon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.yapp.picon.databinding.ActivityMainBinding

abstract class BaseMapActivity: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        setUpMap()
    }

    private fun setUpMap() {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(SecretKeySet.CLIENT_ID)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFrame) as MapFragment?
            ?: run {
                MapFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().add(R.id.mapFrame, it).commit()
                }
            }
        mapFragment.getMapAsync(this)
    }
}