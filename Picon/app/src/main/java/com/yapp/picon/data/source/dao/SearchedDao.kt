package com.yapp.picon.data.source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yapp.picon.domain.entity.SearchedEntity

@Dao
interface SearchedDao {
    @Query("SELECT * FROM searched_table")
    suspend fun selectAll(): List<SearchedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchedEntity: SearchedEntity)

    @Query("DELETE FROM searched_table where title = :title and mapX = :mapX and mapY = :mapY")
    suspend fun delete(title: String, mapX: String, mapY: String)
}