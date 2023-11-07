package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.response.CommunityChangePostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyChangePostViewModel @Inject constructor(
    private val profileService: ProfileService
) : ViewModel() {
    private val _changeItemList = MutableLiveData<List<CommunityChangePostResponse>>()
    val changeItemList: LiveData<List<CommunityChangePostResponse>> = _changeItemList

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    fun getMyChangePost(){
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val changes = profileService.getChangeCommunity()
                _changeItemList.value = changes
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }
}