package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupMessageList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCmuChatViewModel @Inject constructor(private val groupService: GroupService) :
    ViewModel() {
    private val _messageList = MutableLiveData<List<GroupMessageList>>()
    val messageList: LiveData<List<GroupMessageList>> = _messageList

    private val _messageCreated = MutableLiveData<Boolean>()
    val messageCreated: LiveData<Boolean> = _messageCreated

    fun getMessageList(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getMessageList(groupId)
            }.onSuccess {
                _messageList.value = it
            }
        }
    }

    fun sendMessage(text: String, groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.sendMessageList(groupId, text)
            }.onSuccess {
                _messageCreated.value = true
            }.onFailure {
                _messageCreated.value = false
            }
        }
    }
}