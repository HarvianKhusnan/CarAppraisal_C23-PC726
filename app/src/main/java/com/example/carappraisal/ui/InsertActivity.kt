package com.example.carappraisal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.carappraisal.R

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val brand: TextView = findViewById(R.id.textView12)
        val nexBtn : Button = findViewById(R.id.next_btn)

        val intent = intent
        val name = intent.getStringExtra("names")

        brand.text = name

        nexBtn.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

    }
}