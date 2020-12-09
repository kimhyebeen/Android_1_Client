package com.yapp.picon.data.source.yapp

import com.yapp.picon.data.model.*
import okhttp3.MultipartBody

interface YappDataSource {
    suspend fun simpleJoin(simpleJoinRequest: SimpleJoinRequest): SimpleJoinResponse
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun uploadImage(accessToken: String, parts: List<MultipartBody.Part>): List<String>
    suspend fun createPost(accessToken: String, postRequest: PostRequest): PostResponse
    suspend fun requestPosts(accessToken: String): PostsResponse
    suspend fun removePost(accessToken: String, id: Int): DefaultResponse
    suspend fun requestUserInfo(accessToken: String): UserResponse
    suspend fun uploadProfile(accessToken: String, imageUrl: String): UserResponse
    suspend fun requestStatistic(accessToken: String, year: String, month: String): Statistics
}