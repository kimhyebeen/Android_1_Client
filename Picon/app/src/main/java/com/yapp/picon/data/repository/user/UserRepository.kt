package com.yapp.picon.data.repository.user

import com.yapp.picon.data.model.*

interface UserRepository {
    suspend fun saveFirst(firstYN: Boolean)
    suspend fun loadFirst(): Boolean
    suspend fun simpleJoin(simpleJoinRequest: SimpleJoinRequest): SimpleJoinResponse
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun saveAccessToken(accessToken: String)
    suspend fun loadAccessToken(): String
    suspend fun requestUserInfo(accessToken: String): UserResponse
    suspend fun uploadProfile(accessToken: String, imageUrl: String): UserResponse
}