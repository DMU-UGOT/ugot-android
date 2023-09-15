package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.study.StudyGetPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyStudyPostDetailViewModel @Inject constructor(private val profileService: ProfileService): ViewModel() {
    private val _studyItemList = MutableLiveData<StudyGetPost>()
    val studyItemList: LiveData<StudyGetPost> = _studyItemList

    fun getMyPost(studyId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                _studyItemList.value = profileService.getStudy(studyId)
            }
        }
    }
}