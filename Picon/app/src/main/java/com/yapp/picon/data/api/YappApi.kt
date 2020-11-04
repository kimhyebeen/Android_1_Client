package com.yapp.picon.data.api

import com.yapp.picon.data.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface YappApi {
    @GET("displays/post/{id}")
    suspend fun requestPost(
        @Path("id") id: String
    ): Post

    @POST("displays/post")
    suspend fun createPost(
        @Body post: Post
    ): Post
}