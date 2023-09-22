package com.example.ugotprototype.di.api

import com.example.ugotprototype.data.community.CommunityGeneralNewPostData
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.community.CommunityGeneralUpdateViewData
import com.example.ugotprototype.data.response.CommunityGeneralDataResponse
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import retrofit2.Response
import retrofit2.http.*

interface CommunityService {
    @POST("com")
    suspend fun createCommunity(@Body communityGeneralNewPostData: CommunityGeneralNewPostData)

    @GET("com")
    suspend fun getCommunityGeneral(@Query("page") page: Int, @Query("size") size: Int): CommunityGeneralDataResponse

    @GET("com/{postId}")
    suspend fun getCommunityDetailGeneral(@Path("postId") postId: Int): CommunityGeneralPostViewData

    @PATCH("com/{postId}")
    suspend fun updateCommunity(
        @Path("postId") postId: Int,
        @Body updatedData: CommunityGeneralUpdateViewData
    )

    @DELETE("com/{postId}")
    suspend fun deleteCommunity(@Path("postId") postId: Int)
}
