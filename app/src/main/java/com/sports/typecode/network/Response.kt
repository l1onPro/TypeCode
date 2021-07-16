package com.sports.typecode.network

data class PhotoResponse(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)

data class AlbumResponse(
    val userId: Int,
    val id: Int,
    val title: String,
)

data class UserResponse (
    val id : Int,
    val name : String,
    val username : String,
)