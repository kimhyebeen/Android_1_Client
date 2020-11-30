package com.yapp.picon.domain.usecase

import com.yapp.picon.data.repository.post.PostRepository
import com.yapp.picon.data.repository.user.UserRepository
import okhttp3.MultipartBody

class UploadImageUseCase(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        parts: List<MultipartBody.Part>
    ): List<String> =
        userRepository.loadAccessToken().let {
            postRepository.uploadImage(it, parts)
        }
}