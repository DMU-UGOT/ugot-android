package com.example.ugotprototype.ui.team.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamInformationBinding
import com.example.ugotprototype.ui.team.adapter.TeamInformationRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamInformationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamInformationBinding
    private var rvAdapter: TeamInformationRecyclerViewAdapter = TeamInformationRecyclerViewAdapter()
    private val teamInformationViewModel: TeamInformationViewModel by viewModels()

    companion object{
        const val DUMMY_DATA = "githubapi-testad"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_information)
        binding.rvTeamInformation.adapter = rvAdapter

        teamInformationViewModel.getTeamInformationList()

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        teamInformationViewModel.isTeamInforList.observe(this) {
            rvAdapter.setData(it)
        }
    }
}