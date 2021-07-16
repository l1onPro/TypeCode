package com.sports.typecode.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sports.typecode.databinding.ItemPhotoBinding
import com.sports.typecode.network.PhotoResponse

class PhotoAdapter(
    private val photos: ArrayList<PhotoResponse>,
) : ListAdapter<PhotoResponse, PhotoAdapter.DataViewHolder>(PHOTOS_COMPARATOR) {
    class DataViewHolder(private var binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoResponse) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    fun addPhotos(users: List<PhotoResponse>) {
        this.photos.apply {
            clear()
            addAll(users)
        }
    }

    companion object {
        private val PHOTOS_COMPARATOR = object : DiffUtil.ItemCallback<PhotoResponse>() {
            override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}