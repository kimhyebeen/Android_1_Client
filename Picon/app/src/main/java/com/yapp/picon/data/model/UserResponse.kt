package com.yapp.picon.data.model

data class UserResponse (
    val status: Int,
    val errors: String,
    val errorCode: String,
    val errorMessage: String,
    val member: Member
)