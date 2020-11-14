package com.yapp.picon.domain.usecase

import com.yapp.picon.data.source.searched.SearchedRepository
import com.yapp.picon.domain.entity.SearchedEntity

class GetAllSearedUseCase(private val repository: SearchedRepository) {
    suspend operator fun invoke(): List<SearchedEntity> =
        repository.selectAll()
}