package com.zaki.imagegenerator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zaki.imagegenerator.R
import com.zaki.imagegenerator.adapter.ImageAdapter
import com.zaki.imagegenerator.databinding.FragmentImageBinding
import com.zaki.imagegenerator.utils.Resource

class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ImageViewModel
    lateinit var imageAdapter: ImageAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("image", it)
            }

            findNavController().navigate(
                    R.id.action_imageFragment_to_imageDetailFragment,
                    bundle
            )
        }

        observeImage()

        binding.imageRefreshLayout.setOnRefreshListener {
            observeImage()
        }
    }

    private fun observeImage() {
        viewModel.allImage.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { imageResponse ->
                        imageAdapter.differ.submitList(imageResponse.data.memes.toList())

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An Error Occured: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        binding.imageRefreshLayout.isRefreshing = false
    }


    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun setupRecyclerView() {
        imageAdapter = ImageAdapter()
        binding.rvImage.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(activity, 3)
        }
    }
}