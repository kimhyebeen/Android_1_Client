package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.PostsResponse
import com.yapp.picon.data.repository.post.PostRepository

class RequestPostsUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(accessToken: String): PostsResponse =
        postRepository.requestPosts(accessToken)
}