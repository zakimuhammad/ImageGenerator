package com.zaki.imagegenerator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaki.imagegenerator.R
import com.zaki.imagegenerator.databinding.ItemImageBinding
import com.zaki.imagegenerator.model.Data

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root)


    private lateinit var binding: ItemImageBinding

    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(image.url)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(binding.ivImage)

            setOnClickListener {
                onItemClickListener?.let { it(image) }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Data) -> Unit)? = null

    fun setOnItemClickListener(listener: (Data) -> Unit) {
        onItemClickListener = listener
    }
}