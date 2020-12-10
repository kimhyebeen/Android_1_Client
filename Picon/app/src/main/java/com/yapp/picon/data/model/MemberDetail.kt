package com.yapp.picon.data.model

import com.google.gson.annotations.SerializedName

data class MemberDetail (
    @SerializedName("memberDto")
    val member: Member,
    val followInfo: FollowInfo
)