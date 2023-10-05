package com.example.ugotprototype.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.group.GroupEngagementData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEngagementRateViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {
    private val _engagementRate = MutableLiveData<ArrayList<GroupEngagementData>>()
    val engagementRateData: LiveData<ArrayList<GroupEngagementData>> = _engagementRate

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    fun fetchMembers(groupName: String) {
        _isLoadingPage.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                val groupEngagementData = ArrayList<GroupEngagementData>()

                val repositories = apiService.getOrganizationRepositories(groupName)

                for (repository in repositories!!) {
                    val response = apiService.getRepositoryContributors(groupName, repository.name)

                    if (response.isSuccessful) {
                        val contributors = response.body()

                        contributors?.forEach { contributor ->
                            val existingMember =
                                groupEngagementData.find { it.login == contributor.login }

                            if (existingMember != null) {
                                existingMember.contributions += contributor.contributions
                            } else {
                                groupEngagementData.add(
                                    GroupEngagementData(
                                        contributor.login, "", contributor.contributions
                                    )
                                )
                            }
                        }
                    } else {
                        Log.d("test", "API 요청이 실패했거나 커밋 정보가 없습니다.")
                    }
                }

                _engagementRate.value = groupEngagementData
            }.onSuccess {
                _isLoadingPage.value = true
            }.onFailure {
                Log.d("test", it.toString())
                _isLoadingPage.value = true
            }
        }
    }
}