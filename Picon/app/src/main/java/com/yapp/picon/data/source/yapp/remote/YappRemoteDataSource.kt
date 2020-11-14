package com.yapp.picon.data.source.yapp.remote

import com.yapp.picon.data.api.YappApi
import com.yapp.picon.data.model.LoginRequest
import com.yapp.picon.data.model.LoginResponse
import com.yapp.picon.data.model.SimpleJoinRequest
import com.yapp.picon.data.model.SimpleJoinResponse
import com.yapp.picon.data.source.yapp.YappDataSource

class YappRemoteDataSource(
    private val retrofitService: YappApi
) : YappDataSource {
    override suspend fun simpleJoin(simpleJoinRequest: SimpleJoinRequest): SimpleJoinResponse =
        retrofitService.simpleJoin(simpleJoinRequest)

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        retrofitService.login(loginRequest)
}