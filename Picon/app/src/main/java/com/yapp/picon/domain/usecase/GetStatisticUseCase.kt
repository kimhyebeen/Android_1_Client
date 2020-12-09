package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.Statistics
import com.yapp.picon.data.repository.post.PostRepository
import com.yapp.picon.data.repository.user.UserRepository

class GetStatisticUseCase(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        year: String, month: String
    ): Statistics =
        userRepository.loadAccessToken().let {
            postRepository.requestStatistic(it, year, month)
        }
}