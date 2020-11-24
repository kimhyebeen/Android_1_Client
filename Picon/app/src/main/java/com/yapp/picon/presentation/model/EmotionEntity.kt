package com.yapp.picon.presentation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotions")
data class EmotionEntity (
    @PrimaryKey(autoGenerate = true)
    var index: Int,
    var color: String,
    var emotion: String,
)