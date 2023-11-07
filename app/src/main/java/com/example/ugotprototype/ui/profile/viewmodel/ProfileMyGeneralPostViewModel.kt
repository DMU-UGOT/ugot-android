package com.example.ugotprototype.ui.profile.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyGeneralPostViewModel @Inject constructor(
    private val profileService: ProfileService
) : ViewModel() {
    private val _generalItemList = MutableLiveData<List<CommunityGeneralPostResponse>>()
    val generalItemList: LiveData<List<CommunityGeneralPostResponse>> = _generalItemList

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _isDeletePost = MutableLiveData<Boolean>()
    val isDeletePost: LiveData<Boolean> = _isDeletePost

    private val _isDeleteVisible = MutableLiveData<Int>()
    val isDeleteVisible: LiveData<Int> = _isDeleteVisible


    fun getMyPost() {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val generals = profileService.getGeneralCommunity()
                _generalItemList.value = generals
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }

    fun toggleDeleteVisibility() {
        val currentVisibility = _isDeleteVisible.value ?: View.INVISIBLE
        _isDeleteVisible.value =
            if (currentVisibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
    }

    fun deletePost(postId: Int) {
        _isDeletePost.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteGeneralCommunity(postId)
            }.onSuccess {
                _isDeletePost.value = true
            }.onFailure {
                _isDeletePost.value = false
            }
        }
    }

    fun setDeleteVisibility() {
        _isDeleteVisible.value = View.INVISIBLE
    }
}