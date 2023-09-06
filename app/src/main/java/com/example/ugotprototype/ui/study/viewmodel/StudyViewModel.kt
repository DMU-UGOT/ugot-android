package com.example.ugotprototype.ui.study.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.StudyService
import com.example.ugotprototype.data.study.StudyData
import com.example.ugotprototype.data.study.StudyDataResponse
import com.example.ugotprototype.data.study.StudyGetPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(private val studyService: StudyService, private val apiService: ApiService): ViewModel() {
    private val _studyItemList = MutableLiveData<List<StudyGetPost>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val studyItemList: LiveData<List<StudyGetPost>> = _studyItemList

    private val _studyMaxPersonnel = MutableLiveData<Int>()
    var studyMaxPersonnel: LiveData<Int> = _studyMaxPersonnel

    private val _totalPage = MutableLiveData<Int>()
    var totalPage: LiveData<Int> = _totalPage

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _isTokenExpired = MutableLiveData<Boolean>()
    val isTokenExpired: LiveData<Boolean> = _isTokenExpired


    fun getStudyList() {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val pageResponse = studyService.getTeams(_currentPage.value!!, 5)
                val studiesResponse = pageResponse.data

                studiesResponse.forEach { studies ->
                    kotlin.runCatching {
                        val avatarUrl = apiService.getOrganization(
                            studies.gitHubLink
                        )?.avatarUrl
                        studies.avatarUrl = avatarUrl ?: ""
                    }
                }

                _studyItemList.value = studiesResponse
                _totalPage.value = pageResponse.pageInfo.totalPages
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                _isTokenExpired.value = true
            }
        }
    }

    fun setCurrentPage(data: Int) {
        _currentPage.value = data
    }

    fun setTotalPage(data: Int) {
        _totalPage.value = data
    }

    val onPrevButtonClickListener = View.OnClickListener {
        if (_currentPage.value!! > 1) {
            _currentPage.value = _currentPage.value!! - 1
            _isLoadingPage.value = false
        }
    }

    val onNextButtonClickListener = View.OnClickListener {
        if (_currentPage.value!! < _totalPage.value!!) {
            _currentPage.value = _currentPage.value!! + 1
            _isLoadingPage.value = false
        }
    }
}