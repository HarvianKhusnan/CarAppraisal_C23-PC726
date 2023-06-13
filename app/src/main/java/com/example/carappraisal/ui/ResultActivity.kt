package com.example.carappraisal.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carappraisal.R
import com.example.carappraisal.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photo = intent.getParcelableExtra<Bitmap>("photo")
        photo?.let {
            binding.carImgResult.setImageBitmap(photo)
        }
    }
}