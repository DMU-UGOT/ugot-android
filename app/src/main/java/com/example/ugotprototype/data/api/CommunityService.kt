package com.example.ugotprototype.di.api

import com.example.ugotprototype.data.community.*
import com.example.ugotprototype.data.response.CommunityGeneralDataResponse
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import com.example.ugotprototype.data.response.CommunitySearchResponse
import com.example.ugotprototype.data.response.TeamSearchResponse
import com.example.ugotprototype.data.team.TeamSearchHistory
import retrofit2.Response
import retrofit2.http.*

interface CommunityService {
    @POST("com")
    suspend fun createCommunity(@Body communityGeneralNewPostData: CommunityGeneralNewPostData)

    @GET("com")
    suspend fun getCommunityGeneral(@Query("page") page: Int, @Query("size") size: Int): CommunityGeneralDataResponse

    @GET("com/{postId}")
    suspend fun getCommunityDetailGeneral(@Path("postId") postId: Int): CommunityGeneralPostViewData

    @GET("com/post/search")
    suspend fun searchCommunity(@Query("page") page: Int, @Query("keyword") keyword: String): Response<CommunitySearchResponse>

    @GET("com/searchHistory")
    suspend fun searchHistory(): List<CommunitySearchHistory>

    @PATCH("com/{postId}")
    suspend fun updateCommunity(
        @Path("postId") postId: Int,
        @Body updatedData: CommunityGeneralUpdateViewData
    )

    @DELETE("com/{postId}")
    suspend fun deleteCommunity(@Path("postId") postId: Int)

    @DELETE("com/searchHistory/{query}")
    suspend fun deleteSearchHistory(@Path("query") query: String)

    @DELETE("com/searchHistory")
    suspend fun allDeleteSearchHistory()

    @PATCH("com/{postId}/refresh")
    suspend fun refreshCommunity(
        @Path("postId") postId: Int,
        @Body refreshData: CommunityGeneralRefreshData
    )
}
