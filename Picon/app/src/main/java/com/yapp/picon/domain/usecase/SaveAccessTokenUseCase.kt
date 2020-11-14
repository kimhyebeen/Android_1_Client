package com.yapp.picon.domain.usecase

import com.yapp.picon.data.repository.user.UserRepository

class SaveAccessTokenUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(accessToken: String) =
        repository.saveAccessToken(accessToken)
}