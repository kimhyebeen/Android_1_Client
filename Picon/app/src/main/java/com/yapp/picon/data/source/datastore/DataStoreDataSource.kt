package com.yapp.picon.data.source.searched

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {
    suspend fun saveAccessToken(accessToken: String)
    suspend fun loadAccessToken(): Flow<String>
}