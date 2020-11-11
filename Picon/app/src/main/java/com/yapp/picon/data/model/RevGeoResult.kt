package com.yapp.picon.data.model

data class RevGeoResult(
    val status: RevGeoStatus,
    val results: List<RevGeoItem>
)