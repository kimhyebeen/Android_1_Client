package com.yapp.picon.domain.usecase

import com.yapp.picon.data.repository.user.UserRepository

class LoadFirstUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): Boolean =
        repository.loadFirst()
}