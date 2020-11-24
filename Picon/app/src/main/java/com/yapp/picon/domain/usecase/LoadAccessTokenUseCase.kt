package com.yapp.picon.domain.usecase

import com.yapp.picon.data.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class LoadAccessTokenUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): Flow<String> =
        repository.loadAccessToken()
}