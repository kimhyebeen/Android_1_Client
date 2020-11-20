package com.yapp.picon.data.source.searched.remote

import com.yapp.picon.data.api.NaverApi
import com.yapp.picon.data.model.LocalResult
import com.yapp.picon.data.source.searched.NaverDataSource

class NaverRemoteDataSource(
    private val retrofitService: NaverApi
) : NaverDataSource {

    override suspend fun requestLocal(searchWord: String): LocalResult =
        retrofitService.requestLocal(searchWord)
}