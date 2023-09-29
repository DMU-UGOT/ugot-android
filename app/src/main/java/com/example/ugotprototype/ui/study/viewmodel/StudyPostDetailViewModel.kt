package com.example.ugotprototype.ui.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.api.StudyService
import com.example.ugotprototype.data.study.StudyGetPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyPostDetailViewModel @Inject constructor(
    private val studyService: StudyService,
    private val groupService: GroupService
) : ViewModel() {
    private val _studyDetailData = MutableLiveData<StudyGetPost>()
    val studyDetailData: LiveData<StudyGetPost> = _studyDetailData

    private val _isDuplicateGroupPerson = MutableLiveData<Boolean>()
    val isDuplicateGroupPerson: LiveData<Boolean> = _isDuplicateGroupPerson

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
}