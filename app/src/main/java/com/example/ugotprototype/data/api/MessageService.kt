package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.community.CommunityMessageData
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MessageService {
    @POST("messages")
    suspend fun sendMessage(
        @Body messageData: CommunityMessageData,
        @Header("Authorization") token: String
    )
}