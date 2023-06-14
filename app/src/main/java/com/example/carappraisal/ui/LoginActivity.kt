package com.example.carappraisal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.example.carappraisal.R
import com.example.carappraisal.databinding.ActivityLoginBinding
import com.example.carappraisal.viewmodel.LoginViewModel
import com.example.carappraisal.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       binding.clickHere.setOnClickListener {
           val intent = Intent(this, RegisterActivity::class.java)
           startActivity(intent)
       }
    }
}