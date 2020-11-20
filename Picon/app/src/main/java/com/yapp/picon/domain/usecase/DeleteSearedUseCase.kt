package com.yapp.picon.domain.usecase

import com.yapp.picon.data.source.searched.SearchedRepository

class DeleteSearedUseCase(private val repository: SearchedRepository) {
    suspend operator fun invoke(title: String, mapX: String, mapY: String) =
        repository.delete(title, mapX, mapY)
}