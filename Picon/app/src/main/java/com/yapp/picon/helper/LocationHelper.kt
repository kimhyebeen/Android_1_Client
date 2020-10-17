package com.yapp.picon.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

class LocationHelper(private val context: Context) {
    private var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var location: Location? = null

    fun requestLocationPermissions(): Location? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) { // request permissions: 권한이 허용되어 있지 않은 경우 권한 요청
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), RequestCodeSet.LOCATION_REQUEST_CODE.code
            )
        } else { // get data
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        return location
    }
}