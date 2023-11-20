package com.example.ugotprototype.data.response

data class CommunitySearchResponse(
    val content: List<CommunityGeneralPostResponse>,
    val totalPages: Int
)
