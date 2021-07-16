package com.sports.typecode.network

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import kotlin.coroutines.resume

suspend fun <T : Any> Call<T>.exec() = suspendCancellableCoroutine<Response<T>> { continuation ->
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: retrofit2.Response<T>?) {
            continuation.resume(when {
                response == null -> Response.Error(NullPointerException())

                response.isSuccessful -> response.body()
                    ?.run { Response.Success(this) }
                    ?: Response.Error(NullPointerException())

                else -> Response.Error(HttpException(response))

            })
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            continuation.resume(Response.Error(NullPointerException()))
        }
    })
}

sealed class Response<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()
}