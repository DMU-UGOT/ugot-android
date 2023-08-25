package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.sign.SignData
import retrofit2.http.Body
import retrofit2.http.POST

interface SignService {
    @POST("members")
    suspend fun createAccount(@Body signData: SignData)
}