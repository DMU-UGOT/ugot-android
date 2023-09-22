package com.example.ugotprototype.ui.profile.viewmodel

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

    fun getMyPost() {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val teams = profileService.getMyTeams()

                teams.forEach {
                    kotlin.runCatching {
                        val avatarUrl = apiService.getOrganization(it.gitHubLink)?.avatarUrl
                        it.avatarUrl = avatarUrl ?: ""
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
}