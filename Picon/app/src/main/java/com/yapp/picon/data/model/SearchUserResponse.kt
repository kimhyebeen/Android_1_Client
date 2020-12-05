package com.yapp.picon.data.model

data class SearchUserResponse (
    val status: Int,
    val errors: String,
    val errorCode: String,
    val errorMessage: String,
    val members: List<Members>
)