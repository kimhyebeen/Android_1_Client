package com.yapp.picon.data.model

data class DefaultResponse(
    override val status: Int,
    override val errors: String,
    override val errorCode: String,
    override val errorMessage: String
) : Response()