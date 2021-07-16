package com.sports.typecode.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sports.typecode.databinding.SreenPhotosBinding
import com.sports.typecode.network.PhotoResponse
import com.sports.typecode.utils.Status

class PhotoFragment : Fragment() {

    private val viewModel: PhotoViewModel by viewModels()
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SreenPhotosBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = PhotoAdapter(arrayListOf())
        binding.recyclerView.adapter = adapter

        setupObservers()
        setupObserversClickBack()

        return binding.root
    }

    private fun setupObserversClickBack(){
        viewModel.onClickBack.observe(viewLifecycleOwner, {
            if (it == true) this.findNavController().popBackStack()
        })
    }

    private fun setupObservers() {
        viewModel.getPhotos().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { photos -> retrieveList(photos) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun retrieveList(photos: List<PhotoResponse>) {
        adapter.apply {
            addPhotos(photos)
            notifyDataSetChanged()
        }
    }
}