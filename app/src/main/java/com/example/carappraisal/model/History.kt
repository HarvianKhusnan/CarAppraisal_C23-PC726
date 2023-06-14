package com.example.carappraisal.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "history")
data class History(
    val brand: String,
    @PrimaryKey
    val model: String,
    val year: String,
    val grade: String,
    @field:TypeConverters(com.example.carappraisal.data.TypeConverters::class)
    val photo: Bitmap
        ) : Serializable