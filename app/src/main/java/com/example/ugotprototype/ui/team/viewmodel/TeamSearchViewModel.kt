package com.example.ugotprototype.ui.team.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.data.response.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamSearchViewModel @Inject constructor(
    private val apiService: ApiService, private val teamBuildingService: TeamBuildingService
) : ViewModel() {

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> = _teams

    fun searchTeams(query: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                val allMatchingTeams = mutableListOf<Team>()
                var currentPage = 0

                while (true) {
                    val response = teamBuildingService.searchTeams(currentPage, query)
                    val teams = response.body()?.content ?: emptyList()

                    for (team in teams) {
                        if (team.title.contains(query)) {
                            val avatarUrl = apiService.getOrganization(
                                team.gitHubLink
                            )?.avatarUrl
                            team.avatarUrl = avatarUrl ?: ""
                            allMatchingTeams.add(team)
                        }
                    }
                    if (currentPage >= (response.body()?.totalPages!! - 1)) {
                        break
                    }
                    currentPage++
                }
                _teams.value = allMatchingTeams
            }
        }
    }
}