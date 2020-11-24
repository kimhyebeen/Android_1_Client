package com.yapp.picon.presentation.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmotionEntity::class], version = 1)
abstract class EmotionDatabase: RoomDatabase() {
    abstract fun emotionDao(): EmotionDao

    companion object {
        @Volatile
        private var INSTANCE: EmotionDatabase? = null

        fun getInstance(context: Context): EmotionDatabase {
            synchronized(EmotionDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    EmotionDatabase::class.java,
                    "emotions"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE as EmotionDatabase
            }
        }
    }
}