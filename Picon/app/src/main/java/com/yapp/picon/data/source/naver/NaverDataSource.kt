package com.yapp.picon.data.source.searched

import com.yapp.picon.data.model.LocalResult

interface NaverDataSource {
    suspend fun requestLocal(searchWord: String): LocalResult
}