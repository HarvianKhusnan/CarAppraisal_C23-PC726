package com.example.carappraisal.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.carappraisal.model.History

@Dao
interface HistoryDao {
    @Insert
    fun addHistory(history: History)

    @Query("SELECT * FROM history")
    fun getHistory(): LiveData<List<History>>

    @Query("DELETE FROM history WHERE model = :model")
    fun deleteFromHistory(model: String)
}