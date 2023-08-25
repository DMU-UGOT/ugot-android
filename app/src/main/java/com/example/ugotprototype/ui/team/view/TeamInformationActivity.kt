package com.example.ugotprototype.ui.team.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamInformationBinding
import com.example.ugotprototype.di.api.ApiService
import com.example.ugotprototype.data.response.OrgMemberDataResponse
import com.example.ugotprototype.ui.team.adapter.TeamInformationRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TeamInformationActivity : AppCompatActivity() {
    @Inject
    lateinit var apiService: ApiService

    private lateinit var binding: ActivityTeamInformationBinding
    private var rvAdapter: TeamInformationRecyclerViewAdapter = TeamInformationRecyclerViewAdapter()
    private val teamViewModel: TeamViewModel by viewModels()

    companion object {
        var githubOrgName = "githubapi-testad"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_information)

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        dataSet()

        teamViewModel.isTeamInforList.observe(this) {
            rvDataSet(it)
        }
    }

    private fun dataSet() {
        binding.rvTeamInformation.adapter = rvAdapter
        lifecycleScope.launch {
            try {
                teamViewModel.setTeamInforData(apiService.getOrganizationMembers(githubOrgName, "Bearer ${TeamFragment.tokenData}"))
            } catch (_: Exception) {
            }
        }
    }

    private fun rvDataSet(teamInforData: List<OrgMemberDataResponse>) {
        rvAdapter.setData(teamInforData)
    }
}