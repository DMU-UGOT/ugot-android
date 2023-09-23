package com.example.ugotprototype.data.response

data class CommunityGeneralPostResponse (
    val id : Int,
    val title : String,
    val content : String,
    var viewCount : Int?,
    val voteCount : Int?,
    val nickname : String,
    val status : String?,
    val created_at : String,
    val modified_at : String
)
