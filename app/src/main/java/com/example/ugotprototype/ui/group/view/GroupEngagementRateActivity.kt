package com.example.ugotprototype.ui.group.view

import android.content.Intent
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
import com.example.ugotprototype.ui.team.view.TeamFragment
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

        binding.ibEngagementPrev.setOnClickListener {
            finish()
        }
    }

    private fun fetchMembers() {
        lifecycleScope.launch {
            try {
                val members = apiService.getOrganizationMembers(
                    "githubapi-testad", "Bearer ${TeamFragment.TOKEN_DATA}"
                )
                Log.d("members", "$members")
                for (member in members) {
                    val repositories = apiService.getOrganizationRepositories(
                        "githubapi-testad", "Bearer ${TeamFragment.TOKEN_DATA}"
                    )
                    Log.d("repositories", "$repositories")
                    var totalContributions = 0
                    for (repo in repositories) {
                        Log.d("test", repo.name)
                        try {
                            val contributors = apiService.getRepositoryContributors(
                                "githubapi-testad", repo.name, "Bearer ${TeamFragment.TOKEN_DATA}"
                            )

                            val userContributions =
                                contributors?.find { it.login == member.login }?.contributions ?: 0
                            totalContributions += userContributions
                        } catch (e: Exception) {
                        }
                    }
                    groupEngagementData.add(
                        GroupEngagementData(
                            member.login, member.avatarUrl, totalContributions
                        )
                    )
                }
                groupViewModel.setEngagementRate(groupEngagementData)
            } catch (e: Exception) {
            }
        }
    }
}