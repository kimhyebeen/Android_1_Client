package com.yapp.picon.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse (
    val status: Int,
    val errors: String,
    val errorCode: String,
    val errorMessage: String,
    @SerializedName("memberDto")
    val member: Member,
    val followInfo: FollowInfo
)