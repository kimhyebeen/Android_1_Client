package com.yapp.picon.data.source.searched

class NaverRepositoryImpl(
    private val remoteDataSource: NaverDataSource
) : NaverRepository {

    override suspend fun requestLocal(searchWord: String) =
        remoteDataSource.requestLocal(searchWord)
}