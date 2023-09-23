package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.sign.SignAccountData
import com.example.ugotprototype.data.sign.SignData
import com.example.ugotprototype.data.sign.SignTokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignService {
    @POST("members")
    suspend fun createAccount(@Body signData: SignData)

    @POST("members/signIn")
    suspend fun signIn(@Body signAccountData: SignAccountData): SignTokenData

    @GET("members/check/{nickname}")
    suspend fun checkNicknameDuplicate(@Path("nickname") nickname: String): Response<String>
}