package com.zaki.imagegenerator.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaki.imagegenerator.repository.ImageRepository

@Suppress("UNCHECKED_CAST")
class ImageViewModelFactory(
    val app: Application,
    private val imageRepository: ImageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(app, imageRepository) as T
    }
}