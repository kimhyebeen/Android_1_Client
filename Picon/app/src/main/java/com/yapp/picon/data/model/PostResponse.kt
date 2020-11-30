package com.yapp.picon.data.model

data class PostResponse(
    override val status: Int,
    override val errors: String,
    override val errorCode: String,
    override val errorMessage: String
) : Response()