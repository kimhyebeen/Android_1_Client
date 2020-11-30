package com.yapp.picon.presentation.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmotionDao {
    @Query("SELECT * FROM emotions ORDER BY `index` ASC")
    fun getAll(): LiveData<List<EmotionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: EmotionEntity)

    @Delete
    fun delete(item: EmotionEntity)

    @Query("DELETE FROM emotions")
    fun deleteAll()
}