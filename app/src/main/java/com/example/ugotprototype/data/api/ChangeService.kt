package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.change.CommunityChangeNewPostData
import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import com.example.ugotprototype.data.change.CommunityChangeUpdateViewData
import com.example.ugotprototype.data.response.CommunityChangeDataResponse
import retrofit2.http.*

interface ChangeService {
    @POST("classChanges")
    suspend fun createChange(@Body communityChangeNewViewData: CommunityChangeNewPostData)

    @GET("classChanges")
    suspend fun getChange(@Query("page") page: Int, @Query("size") size: Int): CommunityChangeDataResponse

    @GET("classChanges/{classChangeId}")
    suspend fun getChangeDetail(@Path("classChangeId") classChangeId: Int): CommunityChangePostViewData

    @PATCH("classChanges/{classChangeId}")
    suspend fun updateChangeCommunity(
        @Path("classChangeId") classChangeId: Int,
        @Body updatedData: CommunityChangeUpdateViewData
    )

    @DELETE("classChanges/{classChangeId}")
    suspend fun deleteChangeDetail(@Path("classChangeId") classChangeId: Int)

    @PATCH("classChanges/{classChangeId}/refresh")
    suspend fun refreshChange(
        @Path("classChangeId") classChangeId: Int,
        @Body refreshData: CommunityChangeRefreshData
    )
}