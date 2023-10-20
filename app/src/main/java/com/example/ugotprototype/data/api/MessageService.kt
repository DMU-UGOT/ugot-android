package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.profile.ProfileMessageChatViewData
import com.example.ugotprototype.data.profile.ProfileMessageCommentNewPostData
import com.example.ugotprototype.data.response.ProfileMessagePostResponse
import retrofit2.http.*

interface MessageService {
    @GET("messages")
    suspend fun getAllMessage() : List<ProfileMessagePostResponse>

    @POST("messages/send/{userId}")
    suspend fun sendMessage(
        @Path("userId") userId : Int,
        @Body messageData: ProfileMessageCommentNewPostData
    )

    @POST("messages/send/room/{roomId}")
    suspend fun sendRoomMessage(
        @Path("roomId") roomId : Int,
        @Body messageData: ProfileMessageCommentNewPostData
    )

    @GET("messages/{roomId}")
    suspend fun getMessage(
        @Path("roomId") roomId: Int
    ) : ArrayList<ProfileMessageChatViewData>

    @DELETE("messages/delete/{messageId}")
    suspend fun messageDelete(
        @Path("messageId") messageId: Int
    )

    @DELETE("messages/delete/room/{roomId}")
    suspend fun messageRoom(
        @Path("roomId") roomId: Int
    )
}