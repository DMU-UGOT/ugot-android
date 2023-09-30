package com.example.ugotprototype.data.community

data class CommunityGeneralPostViewData(
    val id : Int,
    val title : String,
    val content : String,
    val viewCount : Int?,
    val voteCount : Int?,
    val created_at : String,
    val nickname : String,
    val memberId : Int
)
