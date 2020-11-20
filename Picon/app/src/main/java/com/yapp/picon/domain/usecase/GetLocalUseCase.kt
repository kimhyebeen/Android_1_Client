package com.yapp.picon.domain.usecase

import com.yapp.picon.data.model.LocalResult
import com.yapp.picon.data.source.searched.NaverRepository

class GetLocalUseCase(private val repository: NaverRepository) {
    suspend operator fun invoke(searchWord: String): LocalResult =
        repository.requestLocal(searchWord)
}