package com.example.ugotprototype.di.api

import com.example.ugotprototype.di.api.response.OrgDataResponse
import com.example.ugotprototype.di.api.response.OrgMemberDataResponse
import com.example.ugotprototype.di.api.response.UserDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Member

interface ApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserDataResponse?
    @GET("orgs/{orgName}")
    suspend fun getOrganization(@Path("orgName") orgName: String): OrgDataResponse?
    @GET("orgs/{org}/members")
    suspend fun getOrganizationMembers(@Path("org") org: String): List<OrgMemberDataResponse>
}