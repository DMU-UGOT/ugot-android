package com.example.ugotprototype.ui.profile.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.MessageService
import com.example.ugotprototype.data.response.ProfileMessagePostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMessageViewModel @Inject constructor(
    private val messageService: MessageService
) : ViewModel() {
    private val _getMessageData = MutableLiveData<List<ProfileMessagePostResponse>>()
    val getMessageData: LiveData<List<ProfileMessagePostResponse>> = _getMessageData

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _isDeleteVisible = MutableLiveData<Int>()
    val isDeleteVisible: LiveData<Int> = _isDeleteVisible

    fun getMessageList(){
        viewModelScope.launch {
            kotlin.runCatching {
                val data = messageService.getAllMessage()
                _getMessageData.value = data
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

    fun deleteMessageList(roomId : Int){
        _isDelete.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                messageService.messageRoom(roomId)
            }.onSuccess {
                _isDelete.value = true
            }.onFailure {
                _isDelete.value = false
            }
        }
    }
}