package com.sports.typecode.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sports.typecode.utils.Resource
import kotlinx.coroutines.Dispatchers

class PhotoViewModel : ViewModel() {

    fun getPhotos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
    }
}