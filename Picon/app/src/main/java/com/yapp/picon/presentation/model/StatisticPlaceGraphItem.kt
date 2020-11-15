package com.yapp.picon.presentation.model

import com.broooapps.lineargraphview2.DataModel

data class StatisticPlaceGraphItem (
    var place: String,
    var graphItems: List<DataModel>,
    var size: Int
)