package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.LoginRequest
import com.yapp.picon.data.model.LoginResponse
import com.yapp.picon.data.repository.user.UserRepository

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): LoginResponse =
        repository.login(LoginRequest(email, password))
}