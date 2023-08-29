package com.example.ugotprototype.di.api

import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommunityService {
    @POST("com")
    suspend fun createCommunity(@Body communityGeneralViewData: CommunityGeneralPostViewData)

    @GET("com")
    suspend fun getCommunityGeneral(@Query("page") page: Int, @Query("size") size: Int): Array<CommunityGeneralPostResponse>
}