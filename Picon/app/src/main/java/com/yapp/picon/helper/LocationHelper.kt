package com.yapp.picon.helper

import android.content.Context
import android.location.LocationManager

class LocationHelper(context: Context) {
    private var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

}