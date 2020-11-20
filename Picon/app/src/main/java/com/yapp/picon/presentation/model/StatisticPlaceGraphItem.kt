package com.yapp.picon.presentation.model

data class StatisticPlaceGraphItem (
    var place: String,
    var graphItems: List<ListItemForPlaceGraph>,
    var total: Int
)