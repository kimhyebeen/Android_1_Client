package com.yapp.picon.data.model

data class AddressCount (
    val addrCity: String,
    val addrGu: String,
    val emotionCounts: List<EmotionCount>,
    val total: Int
)