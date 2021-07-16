package com.sports.typecode.api

import com.sports.typecode.network.AlbumResponse
import com.sports.typecode.network.PhotoResponse
import com.sports.typecode.network.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PHApi {
    @GET("/users")
    fun getAllUsers() : Call<List<UserResponse>>

    @GET("/albums")
    fun getAllAlbumsByUserId(
        @Query ("userId") userId : Int
    ) : Call<List<AlbumResponse>>

    @GET("/photos")
    fun getAllPhotoByAlbumId(
        @Query("albumId") albumId : Int
    ) : Call<List<PhotoResponse>>
}