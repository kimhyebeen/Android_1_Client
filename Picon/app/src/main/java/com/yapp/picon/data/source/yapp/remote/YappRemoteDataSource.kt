package com.yapp.picon.data.source.yapp.remote

import com.yapp.picon.data.api.YappApi
import com.yapp.picon.data.model.*
import com.yapp.picon.data.source.yapp.YappDataSource
import okhttp3.MultipartBody

class YappRemoteDataSource(
    private val retrofitService: YappApi
) : YappDataSource {
    override suspend fun simpleJoin(simpleJoinRequest: SimpleJoinRequest): SimpleJoinResponse =
        retrofitService.simpleJoin(simpleJoinRequest)

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        retrofitService.login(loginRequest)

    override suspend fun uploadImage(
        accessToken: String,
        parts: List<MultipartBody.Part>
    ): List<String> =
        retrofitService.uploadImage(accessToken, parts)

    override suspend fun createPost(accessToken: String, postRequest: PostRequest): PostResponse =
        retrofitService.createPost(accessToken, postRequest)

    override suspend fun requestPosts(accessToken: String): PostsResponse =
        retrofitService.requestPosts(accessToken)

    override suspend fun removePost(accessToken: String, id: Int): DefaultResponse =
        retrofitService.removePost(accessToken, id)

    override suspend fun requestUserInfo(accessToken: String): UserResponse =
        retrofitService.requestUserInfo(accessToken)

    override suspend fun uploadProfile(accessToken: String, imageUrl: String): UserResponse =
        retrofitService.uploadProfile(accessToken, imageUrl)

    override suspend fun requestStatistic(
        accessToken: String,
        year: String,
        month: String
    ): Statistics =
        retrofitService.requestStatistics(accessToken, year, month)
}