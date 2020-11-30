package com.yapp.picon.data.api

import com.yapp.picon.data.model.*
import retrofit2.http.*

interface YappApi {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("auth/signIn")
    suspend fun simpleJoin(
        @Body simpleJoinRequest: SimpleJoinRequest
    ): SimpleJoinResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("auth/logIn")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse


    @GET("displays/post/{id}")
    suspend fun requestPost(
        @Path("id") id: String
    ): Post

    @POST("displays/post")
    suspend fun createPost(
        @Body post: Post
    ): Post

    @GET("/display/statistics/{year}/{month}")
    suspend fun requestStatistics(
        @Path("year") year: String,
        @Path("month") month: String
    ): Statistics

    @GET("/display/member/")
    suspend fun requestUserInfo(): UserResponse
}