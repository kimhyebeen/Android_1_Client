package com.yapp.picon.data.source.searched

import com.yapp.picon.domain.entity.SearchedEntity

interface SearchedRepository {
    suspend fun selectAll(): List<SearchedEntity>
    suspend fun insert(searchedEntity: SearchedEntity)
    suspend fun delete(title: String, mapX: String, mapY: String)
}