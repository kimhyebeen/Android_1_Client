package com.yapp.picon.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yapp.picon.data.source.dao.SearchedDao
import com.yapp.picon.domain.entity.SearchedEntity

@Database(entities = [SearchedEntity::class], version = 1, exportSchema = false)
abstract class SearchedDatabase : RoomDatabase() {
    abstract fun searchedDao(): SearchedDao

    companion object {
        private var INSTANCE: SearchedDatabase? = null

        fun getInstance(context: Context): SearchedDatabase? {
            if (INSTANCE == null) {
                synchronized(SearchedDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SearchedDatabase::class.java,
                        "parkingLot.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}