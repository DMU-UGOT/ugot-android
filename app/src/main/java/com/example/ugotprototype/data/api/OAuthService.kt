package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.oauth.LoginGoogleRequestModel
import com.example.ugotprototype.data.oauth.LoginGoogleResponseModel
import com.example.ugotprototype.data.oauth.RequestLoginNaver
import com.example.ugotprototype.data.oauth.ResponseLoginGoogle
import com.example.ugotprototype.data.oauth.ResponseLoginKakao
import com.example.ugotprototype.data.oauth.ResponseLoginNaver
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface OAuthService {
    @POST("auth/naver")
    suspend fun loginNaver(
        @Body body: RequestLoginNaver
    ): ResponseLoginNaver

    @POST("auth/kakao")
    suspend fun loginKakao(
        @Body body: String
    ): ResponseLoginKakao

    @POST("auth/google")
    suspend fun loginGoogle(
        @Body body: String
    ): ResponseLoginGoogle

    @POST("oauth2/v4/token")
    fun getAccessToken(
        @Body request: LoginGoogleRequestModel
    ): Call<LoginGoogleResponseModel>

    companion object {

        private val gson = GsonBuilder().setLenient().create()

        fun loginRetrofit(baseUrl: String): OAuthService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(OAuthService::class.java)
        }
    }
}