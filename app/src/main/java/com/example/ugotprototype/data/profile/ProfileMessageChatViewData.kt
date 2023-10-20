package com.example.ugotprototype.data.profile

// 대화 내용
data class ProfileMessageChatViewData(
    val id: Int,
    val room: Int,
    val content: String,
    val senderName : String,
    val receiverName : String,
    val created_at : String,
    var isDelete: Int
)
