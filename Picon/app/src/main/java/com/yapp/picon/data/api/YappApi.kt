package com.yapp.picon.data.api

import com.yapp.picon.data.model.*
import okhttp3.MultipartBody
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

    @Multipart
    @POST("display/post/images")
    suspend fun uploadImage(
        @Header("AccessToken") accessToken: String,
        @Part parts: List<MultipartBody.Part>
    ): List<String>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/display/post")
    suspend fun createPost(
        @Header("AccessToken") accessToken: String,
        @Body postRequest: PostRequest
    ): PostResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("display/post/")
    suspend fun requestPosts(
        @Header("AccessToken") accessToken: String
    ): PostsResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @DELETE("display/post/{id}")
    suspend fun removePost(
        @Header("AccessToken") accessToken: String,
        @Path("id") id: Int
    ): PostsResponse

    @GET("/display/statistics/{year}/{month}")
    suspend fun requestStatistics(
        @Header("AccessToken") AccessToken: String,
        @Path("year") year: String,
        @Path("month") month: String
    ): Statistics

    @GET("/display/member/")
    suspend fun requestUserInfo(
        @Header("AccessToken") AccessToken: String
    ): UserResponse
}