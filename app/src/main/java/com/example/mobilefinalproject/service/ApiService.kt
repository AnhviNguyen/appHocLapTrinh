package com.example.mobilefinalproject.service

import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://81c7-2001-ee0-4b4a-5730-6968-c9ee-e982-4af2.ngrok-free.app/api/"
    private val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}