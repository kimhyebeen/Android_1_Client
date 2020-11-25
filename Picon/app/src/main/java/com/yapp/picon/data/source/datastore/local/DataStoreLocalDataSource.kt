package com.yapp.picon.data.source.searched.remote

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.yapp.picon.data.source.searched.DataStoreDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreLocalDataSource(
    private val dataStore: DataStore<Preferences>
) : DataStoreDataSource {

    private val firstYN = preferencesKey<Boolean>("FIRST_YN")
    private val accessToken = preferencesKey<String>("ACCESS_TOKEN")

    override suspend fun saveFirst(firstYN: Boolean) {
        dataStore.edit { preferences ->
            preferences[this.firstYN] = firstYN
        }
    }

    override suspend fun loadFirst(): Boolean = dataStore.data
        .map { preferences ->
            preferences[this.firstYN] ?: true
        }.first()

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[this.accessToken] = accessToken
        }
    }

    override suspend fun loadAccessToken(): String =
        dataStore.data
            .map { preferences ->
                preferences[this.accessToken] ?: ""
            }.first()
}