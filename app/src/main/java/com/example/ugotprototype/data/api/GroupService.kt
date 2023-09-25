package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.group.GroupDetailData
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import com.example.ugotprototype.data.group.GroupGetFavoritesList
import com.example.ugotprototype.data.group.GroupGetNotice
import com.example.ugotprototype.data.group.GroupMessageList
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupSetNoticeData
import com.example.ugotprototype.data.group.GroupRegisterData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupService {

    @POST("groups")
    suspend fun registerGroup(@Body body: GroupRegisterData)

    @GET("groups")
    suspend fun getGroupData(): List<GroupMiddleViewData>

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
}