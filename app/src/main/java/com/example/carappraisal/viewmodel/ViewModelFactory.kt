package com.example.carappraisal.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carappraisal.data.RepositoryData

class ViewModelFactory private constructor(private val data: RepositoryData) : ViewModelProvider.NewInstanceFactory()
{

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(data) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(data) as T
        }

        throw IllegalAccessException("class viewModel tidak diketahui" + modelClass.name)
    }

}