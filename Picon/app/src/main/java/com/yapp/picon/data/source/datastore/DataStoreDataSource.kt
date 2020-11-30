package com.yapp.picon.data.source.searched

interface DataStoreDataSource {
    suspend fun saveFirst(firstYN: Boolean)
    suspend fun loadFirst(): Boolean
    suspend fun saveAccessToken(accessToken: String)
    suspend fun loadAccessToken(): String
}