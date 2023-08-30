package com.example.ugotprototype.data.community

data class CommunityGeneralPostViewData(
    val id : String,
    val title : String,
    val content : String,
    val viewCount : Int?,
    val voteCount : Int?,
    val created_at : String,
    val member_id : String
)