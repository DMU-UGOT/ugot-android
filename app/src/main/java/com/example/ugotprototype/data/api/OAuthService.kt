package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.oauth.RequestLoginNaver
import com.example.ugotprototype.data.oauth.ResponseLoginNaver
import retrofit2.http.Body
import retrofit2.http.POST

interface OAuthService {
    @POST("auth/naver")
    suspend fun loginNaver(
        @Body body: RequestLoginNaver
    ): ResponseLoginNaver
}