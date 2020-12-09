package com.yapp.picon.data.repository.post

import com.yapp.picon.data.model.*
import com.yapp.picon.data.source.yapp.YappDataSource
import okhttp3.MultipartBody

class PostRepositoryImpl(
    private val yappDataSource: YappDataSource
) : PostRepository {
    override suspend fun uploadImage(
        accessToken: String,
        parts: List<MultipartBody.Part>
    ): List<String> =
        yappDataSource.uploadImage(accessToken, parts)

    override suspend fun createPost(accessToken: String, postRequest: PostRequest): PostResponse =
        yappDataSource.createPost(accessToken, postRequest)

    override suspend fun requestPosts(accessToken: String): PostsResponse =
        yappDataSource.requestPosts(accessToken)

    override suspend fun removePost(accessToken: String, id: Int): DefaultResponse =
        yappDataSource.removePost(accessToken, id)

    override suspend fun requestStatistic(
        accessToken: String,
        year: String,
        month: String
    ): Statistics =
        yappDataSource.requestStatistic(accessToken, year, month)
}