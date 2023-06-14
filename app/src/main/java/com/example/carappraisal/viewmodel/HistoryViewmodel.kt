package com.example.carappraisal.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import com.example.carappraisal.db.HistoryDao
import com.example.carappraisal.db.HistoryDatabase
import com.example.carappraisal.model.History
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewmodel(application: Application) : AndroidViewModel(application) {

    private var historyDao: HistoryDao? = null
    private var historyDb : HistoryDatabase? = null

    init {
        historyDb = HistoryDatabase.getDatabase(application)
        historyDao = historyDb?.historyDao()
    }

    fun addToHistory(brand: String, photo: Bitmap, year: String, model: String, grade: String){
        CoroutineScope(Dispatchers.IO).launch {
            val history = History(brand,model,year, grade,photo)
            historyDao?.addHistory(history)
        }

    }
}