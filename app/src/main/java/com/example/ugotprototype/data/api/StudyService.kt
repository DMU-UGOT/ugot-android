package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.study.StudyBookmarkData
import com.example.ugotprototype.data.study.StudyData
import com.example.ugotprototype.data.study.StudyDataResponse
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.data.study.StudySearchData
import com.example.ugotprototype.data.study.StudySetPost
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StudyService {
    @GET("studies/createdAt")
    suspend fun getTeams(@Query("page") page: Int, @Query("size") size: Int): StudyDataResponse

    @POST("studies")
    suspend fun setStudies(@Body studyData: StudySetPost)

    @GET("studies/search")
    suspend fun searchStudies(@Query("page") page: Int, @Query("keyword") keyword: String): Response<StudySearchData>

    @POST("studies/bookmark/{postId}")
    suspend fun setBookmark(@Path("postId") postId: Int)

    @GET("studies/bookmark")
    suspend fun getBookmark(): List<StudyBookmarkData>

    @GET("studies/{studyId}")
    suspend fun getStudy(@Path("studyId") studyId: Int): StudyGetPost

    @PATCH("studies/{studyId}/refresh")
    suspend fun refreshStudy(@Path("studyId") studyId: Int)
}