package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.data.response.TeamDataResponse
import com.example.ugotprototype.data.response.TeamSearchResponse
import com.example.ugotprototype.data.team.TeamBookmarkData
import com.example.ugotprototype.data.team.TeamPostData
import com.example.ugotprototype.data.team.TeamSearchHistory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamBuildingService {
    @POST("teams")
    suspend fun createTeam(@Body teamData: TeamPostData)

    @GET("teams/{teamId}")
    suspend fun getTeam(@Path("teamId") teamId: Int): Team

    @GET("teams/createdAt")
    suspend fun getTeams(@Query("page") page: Int, @Query("size") size: Int): TeamDataResponse

    @GET("teams/post/search")
    suspend fun searchTeams(
        @Query("page") page: Int,
        @Query("keyword") keyword: String
    ): Response<TeamSearchResponse>

    @POST("teams/bookmark/{postId}")
    suspend fun setBookmark(@Path("postId") postId: Int)

    @GET("teams/bookmark")
    suspend fun getBookmark(): List<TeamBookmarkData>

    @PATCH("teams/{postId}/refresh")
    suspend fun refreshPost(@Path("postId") postId: Int)

    @GET("teams/searchHistory")
    suspend fun searchHistory(): List<TeamSearchHistory>

    @DELETE("teams/searchHistory/{query}")
    suspend fun deleteSearchHistory(@Path("query") query: String)

    @DELETE("teams/searchHistory")
    suspend fun allDeleteSearchHistory()
}