package com.example.ugotprototype.ui.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.api.StudyService
import com.example.ugotprototype.data.study.StudyGetPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyPostDetailViewModel @Inject constructor(
    private val studyService: StudyService,
    private val groupService: GroupService,
    private val profileService: ProfileService,
    private val sharedPreference: SharedPreference
) : ViewModel() {
    private val _studyDetailData = MutableLiveData<StudyGetPost>()
    val studyDetailData: LiveData<StudyGetPost> = _studyDetailData

    private val _isDuplicateGroupPerson = MutableLiveData<Boolean>()
    val isDuplicateGroupPerson: LiveData<Boolean> = _isDuplicateGroupPerson

    private val _isPostDelete = MutableLiveData<Boolean>()
    val isPostDelete: LiveData<Boolean> = _isPostDelete

    private val _isPostRefresh = MutableLiveData<Boolean>()
    val isPostRefresh: LiveData<Boolean> = _isPostRefresh

    fun sendApplication(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.sendApplication(groupId)
            }.onSuccess {
                _isDuplicateGroupPerson.value = true
            }.onFailure {
                _isDuplicateGroupPerson.value = false
            }
        }
    }

    fun viewSetting(studyId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                studyService.getStudy(studyId)
            }.onSuccess {
                _studyDetailData.value = it
            }
        }
    }

    fun getMyNickName(callback: (String) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                callback(it.nickname)
            }.onFailure {
                callback("")
            }
        }
    }

    fun deleteMyPost(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.deleteStudyPost(postId)
            }.onSuccess {
                _isPostDelete.value = true
            }.onFailure {
                _isPostDelete.value = false
            }
        }
    }

    fun refreshMyPost(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                studyService.refreshStudy(postId)
            }.onSuccess {
                _isPostRefresh.value = true
            }.onFailure {
                _isPostRefresh.value = true
            }
        }
    }
}