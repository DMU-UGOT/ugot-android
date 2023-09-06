package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.study.StudyDataResponse
import com.example.ugotprototype.data.study.StudySearchData
import com.example.ugotprototype.data.study.StudySetPost
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StudyService {
    @GET("studies")
    suspend fun getTeams(@Query("page") page: Int, @Query("size") size: Int): StudyDataResponse

    @POST("studies")
    suspend fun setStudies(@Body studyData: StudySetPost)

    @GET("studies/search")
    suspend fun searchStudies(@Query("page") page: Int, @Query("keyword") keyword: String): Response<StudySearchData>
}