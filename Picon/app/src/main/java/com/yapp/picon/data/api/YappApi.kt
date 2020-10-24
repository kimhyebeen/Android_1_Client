package com.yapp.picon.data.api

import com.yapp.picon.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface YappApi {
    @GET("displays/posts/{id}")
    suspend fun requestPost(
        @Path("id") id: String
    ): Post
}