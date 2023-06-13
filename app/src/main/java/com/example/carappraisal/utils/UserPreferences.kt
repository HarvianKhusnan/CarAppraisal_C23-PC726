package com.example.carappraisal.utils

import android.content.Context

class UserPreferences (context: Context) {

    private val pref = context.getSharedPreferences(_prefs, Context.MODE_PRIVATE)



    companion object{
        private const val _prefs = "pref_user"
    }
}