package com.example.ugotprototype.ui.community.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.CommentService
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.data.community.CommunityGeneralCommentNewPostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityGeneralChatViewModel @Inject constructor(
    private val commentService: CommentService,
    private val sharedPreference: SharedPreference
): ViewModel() {
    private val _communityGeneralChatItemList = MutableLiveData<ArrayList<CommunityGeneralChatViewData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityGeneralChatItemList: LiveData<ArrayList<CommunityGeneralChatViewData>> = _communityGeneralChatItemList

    private val _communityGeneralChatCreateItemList = MutableLiveData<CommunityGeneralCommentNewPostData>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityGeneralChatCreateItemList: LiveData<CommunityGeneralCommentNewPostData> = _communityGeneralChatCreateItemList

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    private val _isDeleteComment = MutableLiveData<Boolean>()
    val isDeleteComment: LiveData<Boolean> = _isDeleteComment

    fun getLoggedInUserId(): Int {
        return sharedPreference.getMemberId()
    }

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

    fun deleteChatDetailText(postId: Int, commentId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                commentService.deleteCommunityComment(postId, commentId)
            }.onSuccess {
                getCommunityDetailList(postId)
            }.onFailure {
                Log.d("ChatDeleteError", it.toString())
            }
        }
    }

    fun newCommunityCommentData(postId: Int, communityGeneralCommentNewPostData: CommunityGeneralCommentNewPostData) {
        viewModelScope.launch {
            kotlin.runCatching {
                commentService.createCommunityComment(postId, communityGeneralCommentNewPostData)
            }.onSuccess {
                getCommunityDetailList(postId)
            }.onFailure {
                    Log.d("ChatGetListError2", it.toString())
            }
        }
    }
}