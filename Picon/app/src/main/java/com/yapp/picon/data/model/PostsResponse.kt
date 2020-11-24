package com.yapp.picon.data.model

data class PostsResponse(
    override val status: Int,
    override val errors: String,
    override val errorCode: String,
    override val errorMessage: String,
    val posts: List<Post>
) : Response()