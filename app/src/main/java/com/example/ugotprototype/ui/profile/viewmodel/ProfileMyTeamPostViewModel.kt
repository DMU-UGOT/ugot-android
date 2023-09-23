package com.example.ugotprototype.ui.profile.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.response.TeamPostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyTeamPostViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val apiService: ApiService
) : ViewModel() {
    private val _teamItemList = MutableLiveData<List<TeamPostResponse>>()
    val teamItemList: LiveData<List<TeamPostResponse>> = _teamItemList

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
                val teams = profileService.getMyTeams()

                teams.forEach {
                    kotlin.runCatching {
                        val avatarUrl = apiService.getOrganization(it.gitHubLink)?.avatarUrl
                        it.avatarUrl = avatarUrl ?: ""
                        it.isDelete = View.INVISIBLE
                    }
                }

                _teamItemList.value = teams
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

    fun setDeleteVisibility() {
        _isDeleteVisible.value = View.INVISIBLE
    }

    fun deletePost(postId: Int) {
        _isDeletePost.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteTeamPost(postId)
            }.onSuccess {
                _isDeletePost.value = true
            }.onFailure {
                _isDeletePost.value = false
            }
        }
    }

    fun getMyBookmark() {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val teams = profileService.getBookmark()

                teams.forEach {
                    kotlin.runCatching {
                        val avatarUrl = apiService.getOrganization(it.gitHubLink)?.avatarUrl
                        it.avatarUrl = avatarUrl ?: ""
                        it.isDelete = View.INVISIBLE
                    }
                }

                Log.d("test", teams.toString())
                _teamItemList.value = teams
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }
}