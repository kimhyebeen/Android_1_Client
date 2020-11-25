package com.yapp.picon.data.source.searched

import com.yapp.picon.data.model.LocalResult
import com.yapp.picon.domain.entity.SearchedEntity

interface SearchRepository {
    suspend fun requestLocal(searchWord: String): LocalResult
    suspend fun selectAll(): List<SearchedEntity>
    suspend fun insert(searchedEntity: SearchedEntity)
    suspend fun delete(title: String, mapX: String, mapY: String)
}