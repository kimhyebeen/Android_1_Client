package com.yapp.picon.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searched_table")
data class SearchedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "mapX")
    var mapX: String = "",
    @ColumnInfo(name = "mapY")
    var mapY: String = ""
)