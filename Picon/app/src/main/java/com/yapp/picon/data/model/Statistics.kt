package com.yapp.picon.data.model

data class Statistics (
    val status: Int,
    val errors: String,
    val errorCode: String,
    val errorMessage: String,
    val emotionCounts: List<EmotionCount>,
    val emotionTotal: Int,
    val addressCounts: List<AddressCount>
)