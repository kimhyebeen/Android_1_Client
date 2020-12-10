package com.yapp.picon.data.model

data class FollowListResponse(
    val status: Int,
    val errors: String,
    val errorCode: String,
    val errorMessage: String,
    val members: List<Member>,
    val followInfo: FollowInfo
)