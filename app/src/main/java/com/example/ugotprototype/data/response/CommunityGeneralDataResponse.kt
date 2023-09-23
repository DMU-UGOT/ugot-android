package com.example.ugotprototype.data.response

data class CommunityGeneralDataResponse (
    val data: List<CommunityGeneralPostResponse>,
    val pageInfo: CommunityGeneralPostPageInfoResponse
)
