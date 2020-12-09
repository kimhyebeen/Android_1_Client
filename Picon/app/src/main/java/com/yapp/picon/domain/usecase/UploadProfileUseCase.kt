package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.UserResponse
import com.yapp.picon.data.repository.user.UserRepository

class UploadProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        imageUrl: String
    ): UserResponse =
        userRepository.loadAccessToken().let {
            userRepository.uploadProfile(it, imageUrl)
        }
}