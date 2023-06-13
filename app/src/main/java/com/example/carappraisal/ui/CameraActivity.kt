package com.example.carappraisal.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ColorSpace.Model
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.carappraisal.R
import com.example.carappraisal.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 200
    private val CAMERA_REQUEST_CODE = 201
    private lateinit var binding: ActivityCameraBinding

    private var currentCameraLayout: ImageView? = null
    private var capturedPhoto: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val processBtn: Button = findViewById(R.id.btn_process)

        processBtn.setOnClickListener {
            if (capturedPhoto != null) {
                val intent = Intent(this, ProcessActivity::class.java)
                intent.putExtra("photo", capturedPhoto)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Capture a photo first", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView3.setOnClickListener {
            currentCameraLayout = binding.frontCamera
            checkCameraPermission()
        }

        binding.rearCam.setOnClickListener {
            currentCameraLayout = binding.rearLayout
            checkCameraPermission()
        }

        binding.rightCamera.setOnClickListener {
            currentCameraLayout = binding.rightCam
            checkCameraPermission()
        }

        binding.camLeft.setOnClickListener {
            currentCameraLayout = binding.leftCam
            checkCameraPermission()
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            startCameraIntent()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            capturedPhoto = data?.extras?.get("data") as? Bitmap
            capturedPhoto?.let {
                val scaledPhoto = Bitmap.createScaledBitmap(
                    it,
                    currentCameraLayout?.width ?: 0,
                    currentCameraLayout?.height ?: 0,
                    true
                )
                currentCameraLayout?.setImageBitmap(scaledPhoto)
            }
        }
    }

}
