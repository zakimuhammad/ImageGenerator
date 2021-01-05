package com.zaki.imagegenerator.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.zaki.imagegenerator.R
import com.zaki.imagegenerator.databinding.FragmentImageDetailBinding
import java.io.File
import java.io.FileOutputStream

class ImageDetailFragment : Fragment() {

    private val args: ImageDetailFragmentArgs by navArgs()

    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = args.image

        Glide.with(this)
            .load(image.url)
            .fitCenter()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.ivImageDetail)

        binding.ivImagePick.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.btnSimpan.setOnClickListener {
//            val fileOutputStream: FileOutputStream? = null
//            val file: File =
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.ivImageDetail.setImageURI(imageUri)
        }
    }
}