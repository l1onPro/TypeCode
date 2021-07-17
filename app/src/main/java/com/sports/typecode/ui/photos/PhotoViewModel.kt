package com.sports.typecode.ui.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sports.typecode.network.NetworkModule
import com.sports.typecode.network.PhotoResponse
import com.sports.typecode.network.Response
import com.sports.typecode.network.exec
import com.sports.typecode.utils.Resource
import kotlinx.coroutines.Dispatchers

class PhotoViewModel : ViewModel() {

    val onClickBack = MutableLiveData(false)
    private val userId = MutableLiveData(-1)

    fun getPhotos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        when (val resp = userId.value?.let { NetworkModule.phApi.getAllAlbumsByUserId(it + 1).exec() }) {
            is Response.Success -> {
                val photos = mutableListOf<PhotoResponse>()
                resp.data.forEach {
                    when (val respPhoto = NetworkModule.phApi.getAllPhotoByAlbumId(it.id).exec()) {
                        is Response.Success -> {
                            photos.addAll(respPhoto.data)
                        }
                        is Response.Error -> {
                            emit(Resource.error(data = null, message = "Что-то пошло не так"))
                        }
                    }
                }
                emit(Resource.success(data = photos))
            }
            is Response.Error -> {
                emit(Resource.error(data = null, message = "Что-то пошло не так"))
            }
        }
    }

    fun setOnClickBack(){
        onClickBack.postValue(true)
    }

    fun setUserId(id:Int){
        userId.postValue(id)
    }
}