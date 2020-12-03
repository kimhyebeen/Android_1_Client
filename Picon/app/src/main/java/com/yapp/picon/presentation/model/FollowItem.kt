package com.yapp.picon.presentation.model

data class FollowItem (
    val id: Int?,
    val image: String,
    val email: String,
    var following: Boolean,
    var follower: Boolean
)