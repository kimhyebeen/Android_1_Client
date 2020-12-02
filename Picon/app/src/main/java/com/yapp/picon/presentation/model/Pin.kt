package com.yapp.picon.presentation.model

data class Pin(
    val id: Int?,
    val imageUrl: String?,
    val rgb: String?,
    var showManyYN: Boolean,
    var showCbYN: Boolean,
    var checkYN: Boolean
)