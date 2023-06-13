package com.example.carappraisal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.carappraisal.data.RepositoryData

class RegisterViewModel(private val data: RepositoryData) : ViewModel() {
    fun registerUser(name: String, username: String, password: String) = data.register(name,username,password)
}