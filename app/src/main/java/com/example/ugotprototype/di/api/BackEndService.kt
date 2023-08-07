package com.example.ugotprototype.di.api

import com.example.ugotprototype.data.team.TeamPostData
import com.example.ugotprototype.di.api.response.TeamDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BackEndService {
    @POST("teams")
    suspend fun createTeam(@Body teamData: TeamPostData)

    @GET("teams")
    suspend fun getTeams(@Query("page") page: Int, @Query("size") size: Int): TeamDataResponse
}