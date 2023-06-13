package com.example.carappraisal.api

import com.example.carappraisal.response.Login
import com.example.carappraisal.response.ResponseAll
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") pw : String,
    ) : ResponseAll

    @POST("login")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") pw: String
    ) : ResponseAll
}