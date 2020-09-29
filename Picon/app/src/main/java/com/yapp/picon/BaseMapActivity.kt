package com.yapp.picon

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback

abstract class BaseMapActivity(
    @LayoutRes private val layoutId: Int = R.layout.activity_main
): AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

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