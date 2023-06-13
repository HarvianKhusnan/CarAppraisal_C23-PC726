package com.example.carappraisal.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Brand (
    val name: String,
    val photo: Int
        ) : Parcelable