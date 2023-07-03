package com.example.ugotprototype.di.api

import android.util.Log
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchUserData(authToken: String) {
        try {
            val response = apiService.getUser("Bearer $authToken")
            if (response.isSuccessful) {
                Log.d("userName", response.body()?.login.toString())
            }
        } catch(_: Exception) { }
    }
}