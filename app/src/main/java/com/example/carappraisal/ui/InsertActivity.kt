package com.example.carappraisal.ui

import com.example.carappraisal.ui.CameraActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.carappraisal.R
import com.google.android.material.textfield.TextInputLayout

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val brand: TextView = findViewById(R.id.textView12)
        val nexBtn : Button = findViewById(R.id.next_btn)
        val layout1 : TextInputLayout = findViewById(R.id.textInputLayout)
        val layout2 : TextInputLayout = findViewById(R.id.textInputLayout2)
        val layout3 : TextInputLayout = findViewById(R.id.textInputLayout3)
        val layout4 : TextInputLayout = findViewById(R.id.textInputLayout4)

        val intent = intent
        val name = intent.getStringExtra("names")

        brand.text = name

        nexBtn.setOnClickListener {
           val input1 =  layout1.editText?.text.toString()
            val input2 =  layout2.editText?.text.toString()
            val input3 =layout3.editText?.text.toString()
            val input4 = layout4.editText?.text.toString()

            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("input1", input1)
            intent.putExtra("input2", input2)
            intent.putExtra("input3", input3)
            intent.putExtra("input4", input4)
            intent.putExtra("names", name)
            startActivity(intent)
        }

    }
}