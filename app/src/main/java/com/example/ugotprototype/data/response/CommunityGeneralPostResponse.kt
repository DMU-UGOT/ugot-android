package com.example.ugotprototype.data.response

data class CommunityGeneralPostResponse (
    val id : String,
    val title : String,
    val content : String,
    val viewCount : Int?,
    val voteCount : Int?,
    val member_id : String,
    val status : String?,
    val created_at : String,
    val modified_at : String
)