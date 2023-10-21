package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.change.CommunityMessageData
import com.example.ugotprototype.data.profile.ProfileMessageCommentNewPostData
import com.example.ugotprototype.data.response.ProfileMessageDataResponse
import com.example.ugotprototype.data.response.ProfileMessageDetailDataResponse
import retrofit2.http.*

interface MessageService {
    @GET("messages")
    suspend fun getAllMessage() : ProfileMessageDataResponse

    @POST("messages/send/{communityId}")
    suspend fun sendMessage(
        @Path("communityId") communityId : Int,
        @Body messageData: CommunityMessageData
    )

    @POST("messages/send/room/{roomId}")
    suspend fun sendRoomMessage(
        @Path("roomId") roomId : Int,
        @Body messageData: ProfileMessageCommentNewPostData
    )

    @GET("messages/{roomId}")
    suspend fun getMessage(
        @Path("roomId") roomId: Int
    ) : ProfileMessageDetailDataResponse

    @DELETE("messages/delete/{messageId}")
    suspend fun messageDelete(
        @Path("messageId") messageId: Int
    )

    @DELETE("messages/delete/room/{roomId}")
    suspend fun messageRoomDelete(
        @Path("roomId") roomId: Int
    )
}