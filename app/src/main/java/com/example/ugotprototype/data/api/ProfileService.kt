package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.profile.ProfileMemberData
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
}