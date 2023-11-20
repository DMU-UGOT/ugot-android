package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.community.CommunitySearchHistory
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import com.example.ugotprototype.di.api.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunitySearchViewModel @Inject constructor(
    private val communityService: CommunityService
) : ViewModel() {

    private val _generals = MutableLiveData<List<CommunityGeneralPostResponse>>()
    val generals: LiveData<List<CommunityGeneralPostResponse>> = _generals

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    private val _postSearchHistory = MutableLiveData<List<CommunitySearchHistory>>()
    val postSearchHistory: LiveData<List<CommunitySearchHistory>> = _postSearchHistory

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> = _isDelete

    private val _isAllDelete = MutableLiveData<Boolean>()
    val isAllDelete: LiveData<Boolean> = _isAllDelete

    private val _isSearch = MutableLiveData<Boolean>()
    val isSearch: LiveData<Boolean> = _isSearch

    fun searchGenerals(query: String) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val allMatchingCommunity = mutableListOf<CommunityGeneralPostResponse>()
                var currentPage = 0

                val response = communityService.searchCommunity(currentPage, query)
                val communities = response.body()?.content ?: emptyList()

                for (community in communities) {
                    if (community.title.contains(query)) {
                        allMatchingCommunity.add(community)
                    }
                }

                _generals.value = allMatchingCommunity
            }.onSuccess {
                _isSearch.value = true
                _isLoadingPage.value = true
            }.onFailure {
                _isSearch.value = false
                _isLoadingPage.value = true
            }
        }
    }

    fun getGeneralPostSearchHistory() {
        viewModelScope.launch {
            kotlin.runCatching {
                communityService.searchHistory()
            }.onSuccess {
                _postSearchHistory.value = it
            }
        }
    }

    fun deleteGeneralPostSearchHistory(query: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                communityService.deleteSearchHistory(query)
            }.onSuccess {
                _isDelete.value = true
            }.onFailure {
                _isDelete.value = false
            }
        }
    }

    fun allDeleteGeneralPostSearchHistory() {
        viewModelScope.launch {
            kotlin.runCatching {
                communityService.allDeleteSearchHistory()
            }.onSuccess {
                _isAllDelete.value = true
            }.onFailure {
                _isAllDelete.value = false
            }
        }
    }
}