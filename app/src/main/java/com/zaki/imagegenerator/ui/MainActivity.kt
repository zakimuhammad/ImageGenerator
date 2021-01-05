package com.zaki.imagegenerator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.zaki.imagegenerator.R
import com.zaki.imagegenerator.databinding.ActivityMainBinding
import com.zaki.imagegenerator.repository.ImageRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ImageViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageRepository = ImageRepository()
        val imageViewModelFactory = ImageViewModelFactory(application, imageRepository)
        viewModel = ViewModelProvider(this, imageViewModelFactory).get(ImageViewModel::class.java)


    }
}