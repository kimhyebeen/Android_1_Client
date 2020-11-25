package com.yapp.picon.data.repository.user

import com.yapp.picon.data.model.LoginRequest
import com.yapp.picon.data.model.LoginResponse
import com.yapp.picon.data.model.SimpleJoinRequest
import com.yapp.picon.data.model.SimpleJoinResponse
import com.yapp.picon.data.source.searched.DataStoreDataSource
import com.yapp.picon.data.source.yapp.YappDataSource

class UserRepositoryImpl(
    private val yappDataSource: YappDataSource,
    private val dataStoreDataSource: DataStoreDataSource
) : UserRepository {
    override suspend fun saveFirst(firstYN: Boolean) =
        dataStoreDataSource.saveFirst(firstYN)

    override suspend fun loadFirst(): Boolean =
        dataStoreDataSource.loadFirst()

    override suspend fun simpleJoin(simpleJoinRequest: SimpleJoinRequest): SimpleJoinResponse =
        yappDataSource.simpleJoin(simpleJoinRequest)

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        yappDataSource.login(loginRequest)

    override suspend fun saveAccessToken(accessToken: String) =
        dataStoreDataSource.saveAccessToken(accessToken)

    override suspend fun loadAccessToken(): String =
        dataStoreDataSource.loadAccessToken()
}