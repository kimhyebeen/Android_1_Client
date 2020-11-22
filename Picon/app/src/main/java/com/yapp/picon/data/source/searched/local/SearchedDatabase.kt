package com.yapp.picon.data.source.searched.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yapp.picon.domain.entity.SearchedEntity

@Database(entities = [SearchedEntity::class], version = 1, exportSchema = false)
abstract class SearchedDatabase : RoomDatabase() {
    abstract fun searchedDao(): SearchedDao
}