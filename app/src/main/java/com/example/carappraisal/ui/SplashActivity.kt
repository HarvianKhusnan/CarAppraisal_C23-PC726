package com.example.carappraisal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.carappraisal.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        Handler().postDelayed({
            val moveIntent = Intent(this@SplashActivity, WelcomePage::class.java)
            startActivity(moveIntent)
            finish()
        }, 3000)
    }
}