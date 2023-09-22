package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.study.StudyGetPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileStudyMyBookmarkViewModel @Inject constructor(private val profileService: ProfileService, private val apiService: ApiService) : ViewModel() {
    private val _studyItemList = MutableLiveData<List<StudyGetPost>>()
    val studyItemList: LiveData<List<StudyGetPost>> = _studyItemList

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    fun getMyBookmark() {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val studies = profileService.getStudyBookmark()

                studies.forEach {
                    kotlin.runCatching {
                        val avatarUrl = apiService.getOrganization(it.gitHubLink)?.avatarUrl
                        it.avatarUrl = avatarUrl ?: ""
                    }
                }

                _studyItemList.value = studies
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isLoadingPage.value = true
            }
        }
    }
}