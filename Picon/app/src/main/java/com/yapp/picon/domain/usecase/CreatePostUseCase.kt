package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.*
import com.yapp.picon.data.repository.post.PostRepository
import com.yapp.picon.data.repository.user.UserRepository

class CreatePostUseCase(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        coordinate: Coordinate,
        imageUrls: List<String>?,
        address: Address,
        emotion: Emotion?,
        memo: String?
    ): PostResponse =
        userRepository.loadAccessToken().let {
            postRepository.createPost(
                it, PostRequest(
                    Post(
                        id = null,
                        coordinate = coordinate,
                        imageUrls = imageUrls,
                        address = address,
                        emotion = emotion,
                        memo = memo,
                        createdDate = null
                    )
                )
            )
        }
}