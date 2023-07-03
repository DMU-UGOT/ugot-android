package com.example.ugotprototype.di.api

import com.example.ugotprototype.di.api.response.UserDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("user")
    suspend fun getUser(@Header("Authorization") authorization: String): Response<UserDataResponse>
}