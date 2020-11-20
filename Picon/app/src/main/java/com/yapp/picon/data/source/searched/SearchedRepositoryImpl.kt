package com.yapp.picon.data.source.searched

import com.yapp.picon.domain.entity.SearchedEntity

class SearchedRepositoryImpl(
    private val localDataSource: SearchedDataSource,
) : SearchedRepository {

    override suspend fun selectAll(): List<SearchedEntity> =
        localDataSource.selectAll()

    override suspend fun insert(searchedEntity: SearchedEntity) =
        localDataSource.insert(searchedEntity)

    override suspend fun delete(title: String, mapX: String, mapY: String) =
        localDataSource.delete(title, mapX, mapY)
}