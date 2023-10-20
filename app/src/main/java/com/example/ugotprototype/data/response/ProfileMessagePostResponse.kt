package com.example.ugotprototype.data.response

data class ProfileMessagePostResponse(
    val id: Int,
    val room: Int,
    val content: String,
    val senderName : String,
    val receiverName : String,
    val created_at : String,
    var isDelete: Int
)