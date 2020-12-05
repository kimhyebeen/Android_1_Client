package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.DefaultResponse
import com.yapp.picon.data.repository.post.PostRepository
import com.yapp.picon.data.repository.user.UserRepository

class RemovePostUseCase(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: Int): DefaultResponse =
        userRepository.loadAccessToken().let {
            postRepository.removePost(it, id)
        }
}