package com.yapp.picon.data.model

data class LocalResult(
    val display: Int,
    val items: List<LocalItem>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)