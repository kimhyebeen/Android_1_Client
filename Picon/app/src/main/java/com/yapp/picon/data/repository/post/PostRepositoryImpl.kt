package com.yapp.picon.data.repository.post

import com.yapp.picon.data.model.DefaultResponse
import com.yapp.picon.data.model.PostRequest
import com.yapp.picon.data.model.PostResponse
import com.yapp.picon.data.model.PostsResponse
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

    override suspend fun deletePost(accessToken: String, id: String): DefaultResponse =
        yappDataSource.deletePost(accessToken, id)
}