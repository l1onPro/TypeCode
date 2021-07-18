package com.sports.typecode.ui.photos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sports.typecode.databinding.ItemPhotoBinding
import com.sports.typecode.network.NetworkModule
import com.sports.typecode.network.PhotoResponse
import com.sports.typecode.utils.ScreenUtils
import kotlinx.coroutines.*
import okhttp3.Request
import java.lang.ref.SoftReference

class PhotoAdapter(
    private val photos: ArrayList<PhotoResponse>,
    private val coroutineScope: CoroutineScope,
) : ListAdapter<PhotoResponse, PhotoAdapter.DataViewHolder>(PHOTOS_COMPARATOR) {
    private var memImages: SoftReference<Map<String, Bitmap>> = SoftReference(mapOf())

    private fun getImageFromMemory(key:String): Bitmap? {
        return memImages.get()?.get(key)
    }

    private fun addImageToMemoryCache(url: String, bitmap: Bitmap){
        val newMap = memImages.get()?.toMutableMap()
        newMap?.put(url, bitmap)
        memImages = SoftReference(newMap)
    }

    inner class DataViewHolder(private var binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoResponse) {
            binding.photo = photo
            binding.executePendingBindings()

            val image = getImageFromMemory(photo.url)
            if (image != null){
                binding.image.setImageBitmap(image)
                binding.progressBar.visibility = View.GONE
            } else {
                coroutineScope.launch {
                    val btEdges = async(Dispatchers.IO) {
                        getImage(photo.url)
                    }
                    withContext(Dispatchers.Main){
                        val bitmap = btEdges.await()
                        addImageToMemoryCache(photo.url, bitmap)
                        binding.image.setImageBitmap(bitmap)
                        binding.image.layoutParams.height = ScreenUtils.getScreenWidth() - ScreenUtils.dp(32)
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

        fun getImage(url:String) : Bitmap {
            val req = Request.Builder().url(url).build()
            val response = NetworkModule.httpClient.newCall(req).execute() // BLOCKING
            return BitmapFactory.decodeStream(response.body?.byteStream())
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