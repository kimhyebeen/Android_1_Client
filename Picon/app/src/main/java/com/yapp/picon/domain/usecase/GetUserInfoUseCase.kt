package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.UserResponse
import com.yapp.picon.data.repository.user.UserRepository

class GetUserInfoUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserResponse =
        userRepository.loadAccessToken().let {
            userRepository.requestUserInfo(it)
        }
}