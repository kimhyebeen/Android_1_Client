package com.yapp.picon.presentation.nav.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.yapp.picon.presentation.model.EmotionDatabase
import com.yapp.picon.presentation.model.EmotionEntity

class EmotionDatabaseRepository(application: Application) {
    private val emotionDatabase = EmotionDatabase.getInstance(application)
    private val emotionDao = emotionDatabase.emotionDao()

    fun getAll(): LiveData<List<EmotionEntity>> {
        return emotionDao.getAll()
    }

    fun insert(item: EmotionEntity) {
        return emotionDao.insert(item)
    }
}