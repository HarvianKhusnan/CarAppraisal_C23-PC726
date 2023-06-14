package com.example.carappraisal.ui.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carappraisal.db.HistoryDao
import com.example.carappraisal.db.HistoryDatabase
import com.example.carappraisal.model.History

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private var hisDao: HistoryDao? = null
    private var historyDb : HistoryDatabase? = null

    init{
        historyDb = HistoryDatabase.getDatabase(application)
        hisDao = historyDb?.historyDao()
    }

    fun getHistory(): LiveData<List<History>>?{
        return hisDao?.getHistory()
    }
}