package com.example.carappraisal.viewmodel

import com.example.carappraisal.data.RepositoryData

class LoginViewModel(private val repositoryData: RepositoryData) {
    fun login(email:String, password: String) = repositoryData.login(email, password)
}