package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.profile.ProfileMemberData
import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.data.response.TeamPostResponse
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.data.study.StudySetPost
import com.example.ugotprototype.data.team.TeamPostData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ProfileService {
    @GET("members/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): ProfileMemberData

    @PATCH("members/{userId}")
    suspend fun setUserInfo(@Path("userId") userId: String, @Body data: ProfileMemberData)

    @DELETE("members/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String)

    @GET("teams/myTeams")
    suspend fun getMyTeams(): List<TeamPostResponse>

    @GET("teams/{teamId}")
    suspend fun getTeam(@Path("teamId") teamId: Int): Team

    @PATCH("teams/{teamId}")
    suspend fun patchTeam(@Path("teamId") teamId: Int, @Body team: TeamPostData)

    @GET("teams/bookmark")
    suspend fun getBookmark(): List<TeamPostResponse>

    @GET("studies/myStudies")
    suspend fun getStudies(): List<StudyGetPost>

    @GET("studies/{studyId}")
    suspend fun getStudy(@Path("studyId") studyId: Int): StudyGetPost

    @PATCH("studies/{studyId}")
    suspend fun patchStudy(@Path("studyId") studyId: Int, @Body study: StudySetPost)

    @GET("studies/bookmark")
    suspend fun getStudyBookmark(): List<StudyGetPost>
}