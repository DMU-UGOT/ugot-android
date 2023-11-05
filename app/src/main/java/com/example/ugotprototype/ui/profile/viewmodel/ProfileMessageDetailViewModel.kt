package com.example.ugotprototype.ui.profile.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.MessageService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.profile.ProfileMessageCommentNewPostData
import com.example.ugotprototype.data.response.ProfileMessageDetailPostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMessageDetailViewModel @Inject constructor(
    private val messageService: MessageService,
    private val profileService: ProfileService,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _getMessageChatData = MutableLiveData<List<ProfileMessageDetailPostResponse>>()
    val getMessageChatData: LiveData<List<ProfileMessageDetailPostResponse>> = _getMessageChatData

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _isDeleteVisible = MutableLiveData<Int>()
    val isDeleteVisible: LiveData<Int> = _isDeleteVisible

    fun getUserNickname(callback: (String) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                callback(it.nickname)
            }
        }
    }

    fun getMessageChatList(roomId : Int){
        viewModelScope.launch {
            kotlin.runCatching {
                val list = messageService.getMessage(roomId)
                _getMessageChatData.value = list.data
            }
        }
    }

    fun newMessageChatList(roomId: Int, profileMessageCommentNewPostData: ProfileMessageCommentNewPostData){
        viewModelScope.launch {
            kotlin.runCatching {
                messageService.sendRoomMessage(roomId, profileMessageCommentNewPostData)
            }.onSuccess {
                getMessageChatList(roomId)
            }.onFailure {
                Log.d("MessageChatListError", it.toString())
            }
        }
    }

    fun toggleDeleteVisibility() {
        val currentVisibility = _isDeleteVisible.value ?: View.INVISIBLE
        _isDeleteVisible.value =
            if (currentVisibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
    }

    fun setDeleteVisibility() {
        _isDeleteVisible.value = View.INVISIBLE
    }

    fun deleteMessageChatList(messageId : Int){
        _isDelete.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                messageService.messageDelete(messageId)
            }.onSuccess {
                _isDelete.value = true
            }.onFailure {
                _isDelete.value = false
            }
        }
    }
}