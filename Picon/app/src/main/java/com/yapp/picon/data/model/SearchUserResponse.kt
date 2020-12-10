package com.yapp.picon.data.model

import com.google.gson.annotations.SerializedName

data class SearchUserResponse (
    val status: Int,
    val errors: String,
    val errorCode: String,
    val errorMessage: String,
    @SerializedName("memberDetailDtos")
    val members: List<MemberDetail>
)