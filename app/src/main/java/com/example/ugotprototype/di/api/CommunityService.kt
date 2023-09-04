package com.example.ugotprototype.di.api

import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import retrofit2.http.*

interface CommunityService {
    @POST("com")
    suspend fun createCommunity(@Body communityGeneralViewData: CommunityGeneralPostViewData)

    @GET("com")
    suspend fun getCommunityGeneral(@Query("page") page: Int, @Query("size") size: Int): Array<CommunityGeneralPostResponse>

    @PATCH("com/{postId}")
    suspend fun updateCommunity(
        @Path("postId") postId: String,
        @Body updatedData: CommunityGeneralPostViewData
    )
}