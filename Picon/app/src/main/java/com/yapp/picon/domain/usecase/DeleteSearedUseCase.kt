package com.yapp.picon.domain.usecase

import com.yapp.picon.data.source.searched.SearchRepository

class DeleteSearedUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(title: String, mapX: String, mapY: String) =
        repository.delete(title, mapX, mapY)
}