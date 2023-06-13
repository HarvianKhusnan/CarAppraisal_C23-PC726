package com.example.carappraisal.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.carappraisal.api.ApiService
import com.example.carappraisal.response.Login
import com.example.carappraisal.response.ResponseAll
import com.example.carappraisal.utils.Result
import java.lang.Exception


class RepositoryData(private val apiService: ApiService) {

    fun register(
        name:String,
        username:String,
        pass: String,
    ): LiveData<Result<ResponseAll>> =
        liveData {
            emit(Result.Loading)
            try{
                val response = apiService.registerUser(name,username,pass)
                if (!response.type.toBoolean()!!){
                    emit(Result.onSucces(response))
                }else{
                    Log.e(TAG, "Register Fail : ${response.msg}")
                    response.msg?.let { Result.onError(it)}?.let { emit(it) }
                }
            }catch (e: Exception){
                Log.e(TAG, "Register Exception: ${e.message.toString()}")
                emit(Result.onError(e.message.toString()))
            }
        }

    fun login(email: String, password: String): LiveData<Result<ResponseAll>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.loginUser(email, password)
            emit(Result.onSucces(response))
        }catch (e: Exception){
            emit(Result.onError(e.message.toString()))
        }
    }
}