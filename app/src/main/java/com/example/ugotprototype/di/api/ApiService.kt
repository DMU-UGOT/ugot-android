package com.example.ugotprototype.di.api

import com.example.ugotprototype.di.api.response.UserDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserDataResponse?
}