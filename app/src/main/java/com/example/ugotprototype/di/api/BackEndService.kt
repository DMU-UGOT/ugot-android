package com.example.ugotprototype.di.api

import com.example.ugotprototype.data.team.TeamPostData
import retrofit2.http.Body
import retrofit2.http.POST

interface BackEndService {
    @POST("teams")
    suspend fun createTeam(@Body teamData: TeamPostData)
}