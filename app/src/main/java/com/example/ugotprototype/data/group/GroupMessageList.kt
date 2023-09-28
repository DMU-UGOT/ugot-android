package com.example.ugotprototype.data.group

data class GroupMessageList (
    val conversationId: Int,
    var nickname: String,
    val content: String,
    val createdAt: String,
    var isDelete: Boolean
)