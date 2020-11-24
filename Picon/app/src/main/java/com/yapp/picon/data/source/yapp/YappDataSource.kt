package com.yapp.picon.data.source.yapp

import com.yapp.picon.data.model.LoginRequest
import com.yapp.picon.data.model.LoginResponse
import com.yapp.picon.data.model.SimpleJoinRequest
import com.yapp.picon.data.model.SimpleJoinResponse

interface YappDataSource {
    suspend fun simpleJoin(simpleJoinRequest: SimpleJoinRequest): SimpleJoinResponse
    suspend fun login(loginRequest: LoginRequest): LoginResponse
}