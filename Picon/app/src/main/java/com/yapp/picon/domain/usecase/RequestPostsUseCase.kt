package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.PostsResponse
import com.yapp.picon.data.repository.post.PostRepository
import com.yapp.picon.data.repository.user.UserRepository

class RequestPostsUseCase(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(): PostsResponse =
        userRepository.loadAccessToken().let {
            postRepository.requestPosts(it)
        }
}