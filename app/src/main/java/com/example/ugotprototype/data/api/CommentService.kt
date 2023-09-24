package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.data.community.CommunityGeneralCommentNewPostData
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @POST("com/{postId}/comment")
    suspend fun createCommunityComment(@Body communityGeneralCommentNewPostData: ArrayList<CommunityGeneralCommentNewPostData>)

    @GET("com/{postId}/comment")
    suspend fun getCommunityCommentGeneral(@Path("postId") postId: Int): ArrayList<CommunityGeneralChatViewData>
}