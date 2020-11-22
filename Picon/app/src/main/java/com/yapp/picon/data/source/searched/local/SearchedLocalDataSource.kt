package com.yapp.picon.data.source.searched.local

import com.yapp.picon.data.source.searched.SearchedDataSource
import com.yapp.picon.domain.entity.SearchedEntity

class SearchedLocalDataSource(
    private val dao: SearchedDao
) : SearchedDataSource {

    override suspend fun selectAll(): List<SearchedEntity> =
        dao.selectAll()

    override suspend fun insert(searchedEntity: SearchedEntity) =
        dao.insert(searchedEntity)

    override suspend fun delete(title: String, mapX: String, mapY: String) =
        dao.delete(title, mapX, mapY)
}