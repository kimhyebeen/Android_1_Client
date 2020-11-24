package com.yapp.picon.data.model

data class SimpleJoinResponse(
    override val status: Int,
    override val errors: String,
    override val errorCode: String,
    override val errorMessage: String,
    val id: Int,
    val identity: String,
    val nickName: String
) : Response()