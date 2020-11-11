package com.yapp.picon.data.model

data class RevGeoLand(
    val type: String,
    val number1: String,
    val number2: String,
    val addition0: RevGeoAddition,
    val addition1: RevGeoAddition,
    val addition2: RevGeoAddition,
    val addition3: RevGeoAddition,
    val addition4: RevGeoAddition,
    val name: String?,
    val coords: RevGeoCoords
)