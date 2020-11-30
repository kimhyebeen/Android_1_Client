package com.yapp.picon.presentation.model

import com.yapp.picon.data.model.EmotionCount

data class StatisticPlaceGraphItem (
    var place: String,
    var graphItems: List<EmotionCount>,
    var total: Int
)