package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import com.example.ugotprototype.data.change.CommunityChangeUpdateViewData
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.community.CommunityGeneralRefreshData
import com.example.ugotprototype.data.community.CommunityGeneralUpdateViewData
import com.example.ugotprototype.data.profile.ProfileMemberData
import com.example.ugotprototype.data.response.*
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.data.study.StudySetPost
import com.example.ugotprototype.data.team.TeamPostData
import retrofit2.http.*

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

    @DELETE("studies/{postId}")
    suspend fun deleteStudyPost(@Path("postId") postId: Int)

    @DELETE("teams/{postId}")
    suspend fun deleteTeamPost(@Path("postId") postId: Int)

    @GET("com/myCommunities")
    suspend fun getGeneralCommunity(): List<CommunityGeneralPostResponse>

    @GET("com/{postId}")
    suspend fun getProfileGeneralDetail(@Path("postId") postId: Int): CommunityGeneralPostViewData

    @PATCH("com/{postId}")
    suspend fun updateProfileGeneralDetail(
        @Path("postId") postId: Int,
        @Body updatedData: CommunityGeneralUpdateViewData
    )

    @PATCH("com/{postId}/refresh")
    suspend fun refreshProfileGeneralDetail(
        @Path("postId") postId: Int,
        @Body refreshData: CommunityGeneralRefreshData
    )

    @DELETE("com/{postId}")
    suspend fun deleteGeneralCommunity(@Path("postId") postId: Int)

    @GET("classChanges/myClassChanges")
    suspend fun getChangeCommunity(): List<CommunityChangePostResponse>

    @GET("classChanges/{classChangeId}")
    suspend fun getChangeDetail(@Path("classChangeId") classChangeId: Int): CommunityChangePostViewData

    @PATCH("classChanges/{classChangeId}/refresh")
    suspend fun refreshChange(
        @Path("classChangeId") classChangeId: Int,
        @Body refreshData: CommunityChangeRefreshData
    )

    @PATCH("classChanges/{classChangeId}")
    suspend fun updateChangeCommunity(
        @Path("classChangeId") classChangeId: Int,
        @Body updatedData: CommunityChangeUpdateViewData
    )

    @DELETE("classChanges/{classChangeId}")
    suspend fun deleteChangeDetail(@Path("classChangeId") classChangeId: Int)
}