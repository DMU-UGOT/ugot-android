package com.example.ugotprototype.data.response

data class CommunityGeneralPostResponse (
    val id : Int,
    val title : String,
    val content : String,
    var viewCount : Int?,
    val voteCount : Int?,
    val nickname : String,
    val created_at : String,
    val memberId : Int
)
