package com.example.ugotprototype.ui.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.StudyService
import com.example.ugotprototype.data.study.StudyGetPost
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

    fun searchStudies(query: String) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val allMatchingTeams = mutableListOf<StudyGetPost>()
                var currentPage = 0

                while (true) {
                    val response = studyService.searchStudies(currentPage, query)
                    val studies = response.body()?.content ?: emptyList()

                    for (study in studies) {
                        if (study.title.contains(query)) {
                            val avatarUrl = apiService.getOrganization(
                                study.gitHubLink
                            )?.avatarUrl
                            study.avatarUrl = avatarUrl ?: ""
                            allMatchingTeams.add(study)
                        }
                    }
                    if (currentPage >= (response.body()?.totalPages!! - 1)) {
                        break
                    }
                    currentPage++
                }
                _studies.value = allMatchingTeams
            }.onSuccess {
                _isLoadingPage.value = true
            }
        }
    }
}