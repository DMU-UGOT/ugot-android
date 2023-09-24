package com.example.ugotprototype.ui.community.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.CommentService
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.data.community.CommunityGeneralCommentNewPostData
import com.example.ugotprototype.data.community.CommunityGeneralNewPostData
import com.example.ugotprototype.di.api.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityGeneralChatViewModel @Inject constructor(
    private val commentService: CommentService
): ViewModel() {
    private val _communityGeneralChatItemList = MutableLiveData<ArrayList<CommunityGeneralChatViewData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityGeneralChatItemList: LiveData<ArrayList<CommunityGeneralChatViewData>> = _communityGeneralChatItemList

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    fun setCommunityGeneralChatData(communityGeneralChatViewData: ArrayList<CommunityGeneralChatViewData>) {
        _communityGeneralChatItemList.value = communityGeneralChatViewData
        _itemCount.value = communityGeneralChatViewData.size
    }

    fun getCommunityDetailList(postId:Int){
        viewModelScope.launch {
            kotlin.runCatching {
                val data = commentService.getCommunityCommentGeneral(postId)
                setCommunityGeneralChatData(data)
            }
        }
    }

    fun newCommunityCommentData(communityGeneralCommentNewPostData: ArrayList<CommunityGeneralCommentNewPostData>) {
        viewModelScope.launch {
            kotlin.runCatching {
                commentService.createCommunityComment(communityGeneralCommentNewPostData)
            }.onSuccess { Log.d("commentSuccess", it.toString()) }
                .onFailure { Log.d("commentFail", it.toString()) }
        }
    }
}