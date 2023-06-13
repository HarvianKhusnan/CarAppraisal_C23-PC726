package com.example.carappraisal.response

import com.google.gson.annotations.SerializedName

data class Login (
        @SerializedName("username")
        val username: String,
        @SerializedName("password")
        val password: String,
)