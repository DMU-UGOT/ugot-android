package com.example.ugotprototype.ui.study.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.StudyService
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.data.study.StudySearchHistory
import com.example.ugotprototype.data.team.TeamSearchHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudySearchViewModel @Inject constructor(
    private val apiService: ApiService, private val studyService: StudyService
) : ViewModel() {

    private val _studies = MutableLiveData<List<StudyGetPost>>()
    val studies: LiveData<List<StudyGetPost>> = _studies

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _postSearchHistory = MutableLiveData<List<StudySearchHistory>>()
    val postSearchHistory: LiveData<List<StudySearchHistory>> = _postSearchHistory

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _isAllDelete = MutableLiveData<Boolean>()
    val isAllDelete: LiveData<Boolean> = _isAllDelete

    private val _isSearch = MutableLiveData<Boolean>()
    val isSearch: LiveData<Boolean> = _isSearch

    fun searchStudies(query: String) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val allMatchingTeams = mutableListOf<StudyGetPost>()
                var currentPage = 0

                val bookmark = studyService.getBookmark()
                val studyId = bookmark.map { it.studyId }

                val response = studyService.searchStudies(currentPage, query)
                val studies = response.body()?.content ?: emptyList()

                for (study in studies) {
                    study.bookmark = study.studyId in studyId
                    if (study.title.contains(query)) {
                        val avatarUrl = apiService.getOrganization(
                            study.gitHubLink
                        )?.avatarUrl
                        study.avatarUrl = avatarUrl ?: ""
                        allMatchingTeams.add(study)
                    }
                }

                _studies.value = allMatchingTeams
            }.onSuccess {
                _isSearch.value = true
                _isLoadingPage.value = true
            }.onFailure {
                _isSearch.value = false
                _isLoadingPage.value = true
            }
        }
    }

    fun sendBookmark(teamId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                studyService.setBookmark(teamId)
            }
        }
    }

    fun getTeamPostSearchHistory() {
        viewModelScope.launch {
            kotlin.runCatching {
                studyService.getStudySearchHistory()
            }.onSuccess {
                _postSearchHistory.value = it
            }
        }
    }

    fun deleteStudyPostSearchHistory(query: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                studyService.deleteStudySearchHistory(query)
            }.onSuccess {
                _isDelete.value = true
            }.onFailure {
                _isDelete.value = false
            }
        }
    }

    fun allDeleteStudyPostSearchHistory() {
        viewModelScope.launch {
            kotlin.runCatching {
                studyService.allDeleteStudySearchHistory()
            }.onSuccess {
                _isAllDelete.value = true
            }.onFailure {
                _isAllDelete.value = false
            }
        }
    }
}