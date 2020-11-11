package com.yapp.picon.data.model

data class RevGeoItem(
    val name: String,
    val code: RevGeoItemCode,
    val region: RevGeoRegion,
    val land: RevGeoLand?
)