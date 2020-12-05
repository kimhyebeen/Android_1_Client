package com.yapp.picon.data.repository.post

import com.yapp.picon.data.model.DefaultResponse
import com.yapp.picon.data.model.PostRequest
import com.yapp.picon.data.model.PostResponse
import com.yapp.picon.data.model.PostsResponse
import okhttp3.MultipartBody

interface PostRepository {
    suspend fun uploadImage(accessToken: String, parts: List<MultipartBody.Part>): List<String>
    suspend fun createPost(accessToken: String, postRequest: PostRequest): PostResponse
    suspend fun requestPosts(accessToken: String): PostsResponse
    suspend fun deletePost(accessToken: String, id: String): DefaultResponse
}