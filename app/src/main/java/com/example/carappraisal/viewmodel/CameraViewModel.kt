package com.example.carappraisal.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    private val _uploadedPhoto = MutableLiveData<Bitmap>()
    val uploadedPhoto: LiveData<Bitmap> get() = _uploadedPhoto

    fun setUploadedPhoto(photo: Bitmap) {
        _uploadedPhoto.value = photo
    }
}