package com.yapp.picon.data.model

data class Member (
    val id: Int,
    val identity: String,
    val nickName: String,
    val role: String,
    val createdDate: String,
    val profileImageUrl: String?,
    val isFollowing: Boolean?
)