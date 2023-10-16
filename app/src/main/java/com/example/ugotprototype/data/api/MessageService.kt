package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.change.CommunityMessageData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageService {
    @POST("messages/send/{userId}")
    suspend fun sendMessage(
        @Path("userId") userId : Int,
        @Body messageData: CommunityMessageData
    )

    @GET("messages/{userId}")
    suspend fun allMessage(
        @Path("userId") userId: Int
    ) : ArrayList<CommunityMessageData>
}