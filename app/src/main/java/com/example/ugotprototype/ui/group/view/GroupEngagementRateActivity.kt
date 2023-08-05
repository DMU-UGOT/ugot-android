package com.example.ugotprototype.ui.group.view

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupEngagementData
import com.example.ugotprototype.databinding.ActivityGroupEngagementRateBinding
import com.example.ugotprototype.di.api.ApiService
import com.example.ugotprototype.ui.group.adapter.GroupEngagementRateRecyclerViewAdapter
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupEngagementRateActivity : AppCompatActivity() {
    @Inject
    lateinit var apiService: ApiService

    private lateinit var binding: ActivityGroupEngagementRateBinding
    private lateinit var groupEngagementRateRecyclerViewAdapter: GroupEngagementRateRecyclerViewAdapter

    private var groupEngagementData = ArrayList<GroupEngagementData>()

    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_engagement_rate)

        fetchMembers()

        groupEngagementRateRecyclerViewAdapter = GroupEngagementRateRecyclerViewAdapter()
        binding.rvRating.adapter = groupEngagementRateRecyclerViewAdapter

        groupViewModel.engagementRateData.observe(this) {
            groupEngagementRateRecyclerViewAdapter.setData(groupEngagementData)
            Log.d("group", "$groupEngagementData")
        }
    }

    private fun fetchMembers() {
        lifecycleScope.launch {
            try {
                val members = apiService.getOrganizationMembers("githubapi-testad")
                for (member in members) {
                    val repositories = apiService.getOrganizationRepositories("githubapi-testad")
                    var totalContributions = 0
                    for (repo in repositories) {
                        val contributors =
                            apiService.getRepositoryContributors("githubapi-testad", repo.name)
                        Log.d("constributors", "$contributors")
                        val userContributions =
                            contributors.find { it.login == member.login }?.contributions ?: 0
                        totalContributions += userContributions
                    }
                    Log.d(
                        "GitHubActivity",
                        "${member.login} 님의 총 커밋 수: $totalContributions, 프로필 이미지 URL: ${member.avatarUrl}"
                    )
                    groupEngagementData.add(
                        GroupEngagementData(
                            member.login, member.avatarUrl, totalContributions
                        )
                    )
                }
                groupViewModel.setEngagementRate(groupEngagementData)
            } catch (e: Exception) {
                Log.d("e", "$e")
            }
        }
    }
}