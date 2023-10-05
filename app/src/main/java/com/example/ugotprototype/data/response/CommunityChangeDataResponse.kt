package com.example.ugotprototype.data.response

data class CommunityChangeDataResponse(
    val data: List<CommunityChangePostResponse>,
    val pageInfo: CommunityChangePostPageInfoResponse
)