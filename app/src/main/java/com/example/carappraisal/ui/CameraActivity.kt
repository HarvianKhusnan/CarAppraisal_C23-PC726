package com.example.carappraisal.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.example.carappraisal.ml.Capstone
import com.example.carappraisal.ui.ui.home.HomeFragment
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Tensor
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

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
            classifyImage(capturedPhoto)
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

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
                val scaledPhoto = Bitmap.createScaledBitmap(it, currentCameraLayout?.width ?: 0, currentCameraLayout?.height ?: 0, true)
                currentCameraLayout?.setImageBitmap(scaledPhoto)
            }
        }
    }

    private fun classifyImage(image: Bitmap?) {
        val model = Capstone.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 256 * 256 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(256 * 256)
        image?.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        var pixel = 0
        for (i in 0 until 256) {
            for (j in 0 until 256) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat(((`val` shr 16) and 0xFF).toFloat() / 255f)
                byteBuffer.putFloat(((`val` shr 8) and 0xFF).toFloat() / 255f)
                byteBuffer.putFloat((`val` and 0xFF).toFloat() / 255f)
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray

        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val classes = arrayOf("Bad Car", "Good Car")
        val predictedClass = classes[maxPos]

        model.close()

        val input1 = intent.getStringExtra("input1")
        val input2 = intent.getStringExtra("input2")
        val input3 = intent.getStringExtra("input3")
        val input4 = intent.getStringExtra("input4")
        val brand = intent.getStringExtra("names")

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("prediction_result", predictedClass)
        intent.putExtra("photo", capturedPhoto)
        intent.putExtra("input1", input1)
        intent.putExtra("input2", input2)
        intent.putExtra("input3", input3)
        intent.putExtra("input4", input4)
        intent.putExtra("names",brand)
        intent.putExtra("photo", capturedPhoto)
        startActivityForResult(intent, PROCESS_REQUEST_CODE)
    }

    companion object {
        private const val PROCESS_REQUEST_CODE = 202
    }


}

