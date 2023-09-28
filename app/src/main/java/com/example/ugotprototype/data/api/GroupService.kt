package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.group.GroupDetailData
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import com.example.ugotprototype.data.group.GroupGetApplicationList
import com.example.ugotprototype.data.group.GroupGetFavoritesList
import com.example.ugotprototype.data.group.GroupGetNotice
import com.example.ugotprototype.data.group.GroupGetUserInfo
import com.example.ugotprototype.data.group.GroupMessageList
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupPostWriteData
import com.example.ugotprototype.data.group.GroupRegisterData
import com.example.ugotprototype.data.group.GroupSetNoticeData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupService {

    @POST("groups")
    suspend fun registerGroup(@Body body: GroupRegisterData)

    @GET("groups")
    suspend fun getGroupData(): List<GroupMiddleViewData>

    @GET("groups")
    suspend fun getTeamPostWriteGroupData(): List<GroupPostWriteData>

    @GET("groups/{groupId}")
    suspend fun getGroupDetailData(@Path("groupId") groupId: Int): GroupDetailData

    @GET("groups/{groupId}/findMembers")
    suspend fun getGroupTeamPersonData(@Path("groupId") groupId: Int): List<GroupDetailTeamInforData>

    @POST("groups/{groupId}/addFavorites")
    suspend fun setGroupFavorites(@Path("groupId") groupId: Int)

    @GET("groups/myFavorites")
    suspend fun getGroupFavorites(): List<GroupGetFavoritesList>

    @POST("groups/{groupId}/notice")
    suspend fun setGroupNotice(@Path("groupId") groupdId: Int, @Body body: GroupSetNoticeData)

    @GET("groups/{groupId}/findNotices")
    suspend fun getGroupNotice(@Path("groupId") groupId: Int): List<GroupGetNotice>

    @GET("groups/{groupId}/findNotices")
    suspend fun getCalendarGroupNotice(@Path("groupId") groupId: Int): ArrayList<GroupGetNotice>

    @GET("groups/{groupId}/conversations")
    suspend fun getMessageList(@Path("groupId") groupId: Int): List<GroupMessageList>

    @POST("groups/{groupId}/conversation")
    suspend fun sendMessageList(@Path("groupId") groupId: Int, @Body body: String)

    @DELETE("groups/{groupId}/quit")
    suspend fun quitGroup(@Path("groupId") groupId: Int)

    @DELETE("groups/{groupId}")
    suspend fun deleteGroup(@Path("groupId") groupId: Int)

    @GET("members/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: Int): GroupGetUserInfo

    @PATCH("groups/{groupId}/{noticeId}")
    suspend fun updateNotice(
        @Path("groupId") groupId: Int,
        @Path("noticeId") noticeId: Int,
        @Body body: GroupSetNoticeData
    )

    @DELETE("groups/{noticeId}/deleteNotice")
    suspend fun deleteNotice(@Path("noticeId") noticeId: Int)

    @DELETE("groups/{postId}/deleteConversation")
    suspend fun deleteConversation(@Path("postId") postId: Int)

    @POST("groups/{groupId}/application")
    suspend fun sendApplication(@Path("groupId") groupId: Int)

    @GET("groups/{groupId}/applications")
    suspend fun getApplicationList(@Path("groupId") groupId: Int): List<GroupGetApplicationList>

    @POST("groups/{groupId}/{applicationId}/accept")
    suspend fun receiveApplication(
        @Path("groupId") groupId: Int,
        @Path("applicationId") application: Int
    )

    @DELETE("groups/{groupId}/{applicationId}/accept")
    suspend fun rejectApplication(
        @Path("groupId") groupId: Int,
        @Path("applicationId") application: Int
    )
}