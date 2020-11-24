package com.yapp.picon.data.model

abstract class Response {
    abstract val status: Int
    abstract val errors: String
    abstract val errorCode: String
    abstract val errorMessage: String
}