package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.change.CommunityMessageData
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageService {
    @POST("messages")
    suspend fun sendMessage(
        @Body messageData: CommunityMessageData
    )
}