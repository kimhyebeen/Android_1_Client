package com.yapp.picon.data.model

data class LoginResponse(
    override val status: Int,
    override val errors: String,
    override val errorCode: String,
    override val errorMessage: String,
    val accessToken: String,
    val refreshToken: String
) : Response()