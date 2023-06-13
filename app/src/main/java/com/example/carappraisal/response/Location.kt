package com.example.carappraisal.response

import com.google.gson.annotations.SerializedName


data class Location(
    val filename: String,
    @SerializedName("line_number")
    val lineNumber: Int
)

