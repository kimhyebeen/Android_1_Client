package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.SimpleJoinRequest
import com.yapp.picon.data.model.SimpleJoinResponse
import com.yapp.picon.data.repository.user.UserRepository

class SimpleJoinUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, password: String, nic: String): SimpleJoinResponse =
        repository.simpleJoin(SimpleJoinRequest(email, password, nic))
}