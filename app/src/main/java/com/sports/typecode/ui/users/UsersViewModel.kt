package com.sports.typecode.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sports.typecode.network.NetworkModule
import com.sports.typecode.network.Response
import com.sports.typecode.network.exec
import com.sports.typecode.utils.Resource
import kotlinx.coroutines.Dispatchers

class UsersViewModel : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        when (val resp = NetworkModule.phApi.getAllUsers().exec()) {
            is Response.Success -> {
                emit(Resource.success(data = resp.data))
            }
            is Response.Error -> {
                emit(Resource.error(data = null, message = "Что-то пошло не так"))
            }
        }
    }
}