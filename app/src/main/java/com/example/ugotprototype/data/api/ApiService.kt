package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.response.Contributor
import com.example.ugotprototype.data.response.GitHubRepository
import com.example.ugotprototype.data.response.OrgDataResponse
import com.example.ugotprototype.data.response.OrgMemberDataResponse
import com.example.ugotprototype.data.response.UserDataResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): UserDataResponse?

    @GET("orgs/{orgName}")
    suspend fun getOrganization(
        @Path("orgName") orgName: String
    ): OrgDataResponse?

    @GET("orgs/{org}/members")
    suspend fun getOrganizationMembers(
        @Path("org") org: String
    ): List<OrgMemberDataResponse>

    @GET("orgs/{org}/repos")
    suspend fun getOrganizationRepositories(
        @Path("org") organization: String
    ): List<GitHubRepository>

    @GET("repos/{org}/{repo}/contributors")
    suspend fun getRepositoryContributors(
        @Path("org") organization: String,
        @Path("repo") repo: String
    ): List<Contributor>?
}