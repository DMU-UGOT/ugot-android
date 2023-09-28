package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupMessageList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupCmuChatViewModel @Inject constructor(
    private val groupService: GroupService, private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _messageList = MutableLiveData<List<GroupMessageList>>()
    val messageList: LiveData<List<GroupMessageList>> = _messageList

    private var _tempMessageList = MutableLiveData<List<GroupMessageList>>()
    val tempMessageList: LiveData<List<GroupMessageList>> = _tempMessageList

    private val _messageCreated = MutableLiveData<Boolean>()
    val messageCreated: LiveData<Boolean> = _messageCreated

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private val _isDeleteConversation = MutableLiveData<Boolean>()
    val isDeleteConversation: LiveData<Boolean> = _isDeleteConversation

    init {
        loadMessageList()
    }

    private fun loadMessageList() {
        _messageList.value = emptyList()
    }

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

    fun getNickname(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getUserInfo(sharedPreference.getMemberId())
            }.onSuccess {
                _nickname.value = it.nickname
            }
        }
    }

    fun iconView(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getMessageList(groupId)
            }.onSuccess {
                val currentList = _messageList.value.orEmpty()
                val updatedList = currentList.map { item ->
                    if (item.nickname == _nickname.value) {
                        item.copy(isDelete = !item.isDelete)
                    } else {
                        item
                    }
                }
                _messageList.value = updatedList
            }
        }
    }

    fun deleteConversation(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.deleteConversation(postId)
            }.onSuccess {
                _isDeleteConversation.value = true
            }.onFailure {
                _isDeleteConversation.value = false
            }
        }
    }
}