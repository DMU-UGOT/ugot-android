package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.response.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePostActivityViewModel @Inject constructor(private val profileService: ProfileService): ViewModel() {
    private val _teamItemList = MutableLiveData<Team>()
    val teamItemList: LiveData<Team> = _teamItemList

    fun getMyPost(teamId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                _teamItemList.value = profileService.getTeam(teamId)
            }
        }
    }
}