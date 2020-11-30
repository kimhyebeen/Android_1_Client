package com.yapp.picon.domain.usecase

import com.yapp.picon.data.repository.user.UserRepository

class SaveFirstUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(firstYN: Boolean) =
        repository.saveFirst(firstYN)
}