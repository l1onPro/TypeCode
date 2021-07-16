package com.sports.typecode.network

import com.google.gson.GsonBuilder
import com.sports.typecode.api.PHApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    const val PH_BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val httpClient: OkHttpClient = OkHttpClient()

    private fun OkHttpClient() : OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(logging)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        return builder.build()
    }

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private fun retrofitCurWeather() : Retrofit =
        Retrofit.Builder()
            .baseUrl(PH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()

    val phApi = retrofitCurWeather().create(PHApi::class.java)
}