package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.data.response.TeamDataResponse
import com.example.ugotprototype.data.response.TeamSearchResponse
import com.example.ugotprototype.data.team.TeamBookmarkData
import com.example.ugotprototype.data.team.TeamPostData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamBuildingService {
    @POST("teams")
    suspend fun createTeam(@Body teamData: TeamPostData)

    @GET("teams/{teamId}")
    suspend fun getTeam(@Path("teamId") teamId: Int): Team

    @GET("teams")
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
}