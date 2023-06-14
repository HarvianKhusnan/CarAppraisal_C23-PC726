package com.example.carappraisal.ui

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carappraisal.databinding.ActivityResultBinding
import com.example.carappraisal.model.History
import com.example.carappraisal.viewmodel.HistoryViewmodel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: HistoryViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = HistoryViewmodel(application)

        val photo = intent.getParcelableExtra<Bitmap>("photo")
        val predict = intent.getStringExtra("prediction_result") ?:""
        val brand = intent.getStringExtra("names") ?:""
        val brand_type = intent.getStringExtra("input2") ?:""
        val year = intent.getStringExtra("input1")?:""
        photo?.let {
            binding.carImgResult.setImageBitmap(photo)
        }
        binding.gradeCarResult.text = predict
        binding.yearResult.text = year
        binding.brandResult.text = brand
        binding.modelBrand.text = brand_type

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.saveBtn.setOnClickListener {
            if (photo != null) {
                viewModel.addToHistory(brand,photo,year,brand_type,predict)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}