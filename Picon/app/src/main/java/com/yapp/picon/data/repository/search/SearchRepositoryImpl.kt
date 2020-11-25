package com.yapp.picon.data.repository.search

import com.yapp.picon.data.model.LocalResult
import com.yapp.picon.data.model.RevGeoResult
import com.yapp.picon.data.source.revgeo.RevGeoDataSource
import com.yapp.picon.data.source.searched.NaverDataSource
import com.yapp.picon.data.source.searched.SearchRepository
import com.yapp.picon.data.source.searched.SearchedDataSource
import com.yapp.picon.domain.entity.SearchedEntity

class SearchRepositoryImpl(
    private val naverDataSource: NaverDataSource,
    private val searchedDataSource: SearchedDataSource,
    private val revGeoDataSource: RevGeoDataSource
) : SearchRepository {

    override suspend fun requestLocal(searchWord: String): LocalResult =
        naverDataSource.requestLocal(searchWord)

    override suspend fun selectAll(): List<SearchedEntity> =
        searchedDataSource.selectAll()

    override suspend fun insert(searchedEntity: SearchedEntity) =
        searchedDataSource.insert(searchedEntity)

    override suspend fun delete(title: String, mapX: String, mapY: String) =
        searchedDataSource.delete(title, mapX, mapY)

    override suspend fun requestRevGeo(
        coords: String,
        orders: String,
        output: String
    ): RevGeoResult =
        revGeoDataSource.requestRevGeo(coords, orders, output)

}