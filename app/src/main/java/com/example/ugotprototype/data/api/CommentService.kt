package com.example.ugotprototype.data.api

import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.data.community.CommunityGeneralCommentNewPostData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @POST("com/{postId}/comment")
    suspend fun createCommunityComment(
        @Path("postId") postId: Int,
        @Body communityGeneralCommentNewPostData: CommunityGeneralCommentNewPostData
    )

    @GET("com/{postId}/comment")
    suspend fun getCommunityCommentGeneral(@Path("postId") postId: Int): ArrayList<CommunityGeneralChatViewData>

    @DELETE("com/{postId}/comment/{commentId}")
    suspend fun deleteCommunityComment(
        @Path("postId") postId: Int,
        @Path("commentId") commentId: Int
    )
}