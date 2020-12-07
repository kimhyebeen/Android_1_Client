package com.yapp.picon.data.repository.post

import com.yapp.picon.data.model.*
import okhttp3.MultipartBody

interface PostRepository {
    suspend fun uploadImage(accessToken: String, parts: List<MultipartBody.Part>): List<String>
    suspend fun createPost(accessToken: String, postRequest: PostRequest): PostResponse
    suspend fun requestPosts(accessToken: String): PostsResponse
    suspend fun removePost(accessToken: String, id: Int): DefaultResponse
    suspend fun requestStatistic(accessToken: String, year: String, month: String): Statistics
}